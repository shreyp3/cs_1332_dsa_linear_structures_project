package apply;

import implement.ArrayDeque;
import refactor.EndlessLinkedList;
import refactor.StaticEndlessLinkedList;

import java.util.Iterator;
import java.util.Random;

public class Quackify implements StaticQuackify {

    private final EndlessLinkedList<String> playlist = new EndlessLinkedList<>();
    private boolean playing = false;
    private String currentSong;
    private int currentSongIndex;
    private Iterator<String> playSongs;
    private Random rand;
    private final ArrayDeque<String> undoTrack = new ArrayDeque<>();
    private final ArrayDeque<String> undoElement = new ArrayDeque<>();
    private final ArrayDeque<Integer> undoIndex = new ArrayDeque<>();
    private final ArrayDeque<String> redoTrack = new ArrayDeque<>();
    private final ArrayDeque<String> redoElement = new ArrayDeque<>();
    private final ArrayDeque<Integer> redoIndex = new ArrayDeque<>();

    /**
     * Constructs a new {@link Quackify}.
     */
    public Quackify() {
        this.rand = new Random();
    }

    /**
     * Constructs a new {@link Quackify} given a Random Object.
     * @param rand is the Random object being passed in.
     */
    public Quackify(Random rand) {
        this.rand = rand;
    }

    @Override
    public void play() {
        if (getPlaylist().size() == 0) {
            throw new IllegalStateException("Playlist is empty");
        }
        if (isPlaying()) {
            throw new IllegalStateException("Music is already playing");
        }
        playing = true;
        currentSongIndex = 0;
        playSongs = getPlaylist().iterator();
        currentSong = playSongs.next();
    }

    @Override
    public void stop() {
        if (getPlaylist().size() == 0) {
            throw new IllegalStateException("Playlist is empty");
        }
        if (!isPlaying()) {
            throw new IllegalStateException("Music is already stopped");
        }
        currentSongIndex = 0;
        currentSong = null;
        playSongs = null;
        playing = false;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public StaticEndlessLinkedList<String> getPlaylist() {
        return playlist;
    }

    @Override
    public int size() {
        return getPlaylist().size();
    }

    @Override
    public String currentSong() {
        if (!isPlaying()) {
            throw new IllegalStateException("No song is currently playing");
        }
        return currentSong;
    }

    @Override
    public String nextSong() {
        if (!isPlaying()) {
            throw new IllegalStateException("No song is currently playing");
        }
        if (getPlaylist().size() == 0) {
            throw new IllegalStateException("Playlist is empty");
        }
        currentSong = playSongs.next();
        currentSongIndex++;
        if (currentSongIndex == getPlaylist().size()) {
            currentSongIndex = 0;
        }
        return currentSong;
    }

    @Override
    public void addSong(String song) {
        if (isPlaying()) {
            throw new IllegalStateException("Cannot add songs if music is currently playing");
        }
        if (song == null || song.isEmpty()) {
            throw new IllegalArgumentException("No song title");
        }
        getPlaylist().addFirst(song);
        undoTrack.addFirst("addSong");
        undoElement.addFirst(song);
        undoIndex.addFirst(0);
        redoTrack.clear();
        redoElement.clear();
        redoIndex.clear();
    }

    @Override
    public void removeSong(String song) {
        if (isPlaying()) {
            throw new IllegalStateException("Cannot remove songs if music is currently playing");
        }
        if (song == null || song.isEmpty()) {
            throw new IllegalArgumentException("No song title");
        }
        undoElement.addFirst(song);
        undoIndex.addFirst(getPlaylist().removeValue(song));
        undoTrack.addFirst("removeSong");
        redoTrack.clear();
        redoElement.clear();
        redoIndex.clear();
    }

    @Override
    public void reverse() {
        if (isPlaying()) {
            throw new IllegalStateException("Cannot modify playlist if music is currently playing");
        }
        if (getPlaylist().size() == 0) {
            throw new IllegalStateException("Playlist is empty");
        }
        getPlaylist().reverse();
        undoTrack.addFirst("reverse");
        redoTrack.clear();
        redoElement.clear();
        redoIndex.clear();
    }

    @Override
    public String randomSong() {
        if (!isPlaying()) {
            throw new IllegalStateException("Music is not playing");
        }
        int randIndex = rand.nextInt(playlist.size());
        playSongs = getPlaylist().iterator();
        for (int i = 0; i < randIndex; i++) {
            nextSong();
        }
        return nextSong();
    }

    @Override
    public boolean isPalindrome() {
        ArrayDeque<String> arrayDeque = new ArrayDeque<>(getPlaylist());
        if (arrayDeque.size() == 0) {
            throw new IllegalStateException("Playlist is empty");
        }
        for (int i = 0; i < arrayDeque.size(); i++) {
            if (arrayDeque.size() == 1) {
                return true;
            } else if (!arrayDeque.removeFirst().equals(arrayDeque.removeLast())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void undo() {
        if (undoTrack.size() == 0) {
            throw new IllegalStateException("Nothing to undo");
        }
        if (isPlaying()) {
            throw new IllegalStateException("Cannot modify playlist while music is playing");
        }
        String lastCommand = undoTrack.removeFirst();
        if (lastCommand.equals("addSong")) {
            String lastSong = undoElement.removeFirst();
            int lastIndex = undoIndex.removeFirst();
            getPlaylist().removeAtIndex(lastIndex);
            redoTrack.addFirst("removeSong");
            redoElement.addFirst(lastSong);
            redoIndex.addFirst(lastIndex);
        } else if (lastCommand.equals("removeSong")) {
            String lastSong = undoElement.removeFirst();
            int lastIndex = undoIndex.removeFirst();
            getPlaylist().addAtIndex(lastIndex, lastSong);
            redoTrack.addFirst("addSong");
            redoElement.addFirst(lastSong);
            redoIndex.addFirst(lastIndex);
        } else if (lastCommand.equals("reverse")) {
            getPlaylist().reverse();
            redoTrack.addFirst("reverse");
        }
    }

    @Override
    public void redo() {
        if (redoTrack.size() == 0) {
            throw new IllegalStateException("Nothing to redo");
        }
        if (isPlaying()) {
            throw new IllegalStateException("Cannot modify playlist while music is playing");
        }
        String lastCommand = redoTrack.removeFirst();
        if (lastCommand.equals("addSong")) {
            int lastIndex = redoIndex.removeFirst();
            String lastSong = redoElement.removeFirst();
            getPlaylist().removeValue(lastSong);
            undoTrack.addFirst("removeSong");
            undoElement.addFirst(lastSong);
            undoIndex.addFirst(lastIndex);
        } else if (lastCommand.equals("removeSong")) {
            int lastIndex = redoIndex.removeFirst();
            String lastSong = redoElement.removeFirst();
            getPlaylist().addAtIndex(lastIndex, lastSong);
            undoTrack.addFirst("addSong");
            undoElement.addFirst(lastSong);
            undoIndex.addFirst(lastIndex);
        } else if (lastCommand.equals("reverse")) {
            getPlaylist().reverse();
            undoTrack.addFirst("reverse");
        }
    }
}