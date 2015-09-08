package fosia.fosia;

import java.util.Date;
import java.util.TreeMap;

/**
 * Created by smp on 04/07/15.
 */
public class Messages {
    private TreeMap<String, String> messages;

    public Messages() {
        this.messages = new TreeMap<String, String>();
    }

    public TreeMap getMessages() {
        return messages;
    }

    public void setMessages(TreeMap messages) {
        this.messages = messages;
    }


}
