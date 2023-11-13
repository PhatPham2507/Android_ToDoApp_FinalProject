package tdtu.finalproject;

public class Event {
    String id,title, description, date, time, AnEvent;


    public Event(String id,String title, String description, String date, String time, String anEvent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        AnEvent = anEvent;
    }
    public Event(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAnEvent() {
        return AnEvent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAnEvent(String anEvent) {
        AnEvent = anEvent;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", AnEvent='" + AnEvent + '\'' +
                '}';
    }
}
