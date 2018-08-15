import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

public class Updates {

    boolean ok;
    List<Update> result;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }
}
