package example.DoorListener;

public class Door {
    private DoorListener listener;

    public void addListener(DoorListener doorListener) {
        this.listener = doorListener;
    }

    public void send() {
        for (int i = 0; i < 10; i++) {
            listener.onMessage("open the door");
        }
    }
}
