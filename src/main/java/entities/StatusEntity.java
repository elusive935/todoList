package entities;

import javax.persistence.*;

@Entity
@Table(name="STATUSES")
@NamedQuery(name="StatusEntity.byTaskId", query="from StatusEntity where idTask=?")
public class StatusEntity {
    @Id
    @GeneratedValue
    private int idStatus;
    private Integer status;
    private Integer idTask;

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusEntity that = (StatusEntity) o;

        if (idStatus != that.idStatus) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStatus;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
