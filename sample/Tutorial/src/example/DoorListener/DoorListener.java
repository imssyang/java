package example.DoorListener;

import java.util.EventListener;

public interface DoorListener extends EventListener {
    void onMessage(String message);
}
