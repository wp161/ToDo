package application.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a single to-do
 */
public class Todo {

    /**
     * ID of a Todo
      */
    private Integer id;
    /**
     * Text description of a Todo
     */
    private String text;
    /**
     * Status of completion of a Todo
     */
    private Boolean completed;
    /**
     * Due date of a Todo
     */
    private LocalDate due;
    /**
     * Priority of a Todo
     */
    private Integer priority;
    /**
     * Category of a Todo
     */
    private String category;


    /**
     * Represents the default value of each field in Todo
     */
    public static final Integer HIGHEST_PRIORITY = 3;
    /**
     * Represents the default value of each field in Todo
     */
    public static final Integer LOWEST_PRIORITY = 1;
    /**
     * Represents the default value of each field in Todo
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Represents the default value of each field in Todo
     */
    public static final String NULL_VALUE = "?";
    /**
     * Represents the default value of each field in Todo
     */
    public static final String TEXT = null;
    /**
     * Represents the default value of each field in Todo
     */
    public static final Boolean COMPLETED = false;
    /**
     * Represents the default value of each field in Todo
     */
    public static final LocalDate DUE_DATE = null;
    /**
     * Represents the default value of each field in Todo
     */
    public static final Integer PRIORITY = null;
    /**
     * Represents the default value of each field in Todo
     */
    public static final String CATEGORY = null;

    /**
     * Constructs a new Todo
     * @param id Todo's ID
     * @param text Todo's text description
     * @param completed Todo's completion status
     * @param due Todo's due date
     * @param priority Todo's priority
     * @param category Todo's category
     */
    public Todo(Integer id, String text, Boolean completed, LocalDate due, Integer priority, String category) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.due = due;
        this.priority = priority;
        this.category = category;
    }

    /**
     * Gets Todo's ID
     * @return todo's ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Gets Todo's text description
     * @return todo's text description
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets Todo's completion status
     * @return Todo's completion status
     */
    public Boolean getCompleted() {
        return this.completed;
    }

    /**
     * Gets Todo's due date
     * @return Todo's due date
     */
    public LocalDate getDue() {
        return this.due;
    }

    /**
     * Gets Todo's priority
     * @return Todo's priority
     */
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * Gets Todo's category
     * @return Todo's category
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Sets a Todo to completed
     */
    public void setCompleted() {
        this.completed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Todo)) {
            return false;
        }
        Todo todo = (Todo) o;
        return Objects.equals(getId(), todo.getId()) &&
            Objects.equals(getText(), todo.getText()) &&
            Objects.equals(getCompleted(), todo.getCompleted()) &&
            Objects.equals(getDue(), todo.getDue()) &&
            Objects.equals(getPriority(), todo.getPriority()) &&
            Objects.equals(getCategory(), todo.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(getId(), getText(), getCompleted(), getDue(), getPriority(), getCategory());
    }

    @Override
    public String toString() {
        return "Todo{" +
            "id=" + id +
            ", text='" + text + '\'' +
            ", completed=" + completed +
            ", due=" + due +
            ", priority=" + priority +
            ", category='" + category + '\'' +
            '}';
    }
}
