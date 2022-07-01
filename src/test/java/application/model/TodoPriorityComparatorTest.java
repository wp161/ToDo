package application.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class TodoPriorityComparatorTest {
    TodoPriorityComparator c;
    LocalDate date = LocalDate.of(2019, 4, 24);
    Todo t1, t2, t3;
    @BeforeEach
    public void setUp() throws Exception {
        c = new TodoPriorityComparator();
        t1 = new Todo(1,"text",false, date, 1, "home");
        t2 = new Todo(1,"text",false, date, 2, "home");
        t3 = new Todo(1,"text",false, date, null, "home");
    }

    @Test
    public void compare() {
        assertEquals(0, c.compare(t3,t3));
        assertEquals(1, c.compare(t3, t1));
        assertEquals(-1, c.compare(t1, t3));
        assertEquals(-1, c.compare(t1, t2));

    }
}