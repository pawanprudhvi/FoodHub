package FoodHub.Hub.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tables {

    public Tables(int capacity) {
        this.capacity=capacity;
    }

    public Long getTableId() {
        return tableId;
    }

    public Tables(Long tableId, int capacity, UserEntity user, List<TableReservations> reservations) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.user = user;
        this.reservations = reservations;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TableReservations> getReservations() {
        return reservations;
    }

    public void setReservations(List<TableReservations> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Tables{" +
                "tableId=" + tableId +
                ", capacity=" + capacity +
                ", user=" + user +
                ", reservations=" + reservations +
                '}';
    }

    public Tables()
    {

    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long tableId;

    private int capacity;

    @ManyToOne
    private UserEntity user;


    @OneToMany(mappedBy="tables")
    private List<TableReservations> reservations;
}
