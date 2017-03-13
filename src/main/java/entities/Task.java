package entities;

/**
 * Created by alena.nikiforova on 12.03.2017.
 */
public class Task {
    private String text;
    private boolean status;
    private int idTask;

    public Task() {
    }

    public Task(String text, boolean status, int idTask) {
        this.text = text;
        this.status = status;
        this.idTask = idTask;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
}
