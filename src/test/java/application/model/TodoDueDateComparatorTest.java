package application.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class TodoDueDateComparatorTest {
    TodoDueDateComparator c;
    LocalDate earlierDate = LocalDate.of(2019, 4, 24);
    LocalDate laterDate = LocalDate.of(2021, 6,17);
    Todo t1, t2, t3, t4;
    @BeforeEach
    public void setUp() throws Exception {
        c = new TodoDueDateComparator();
        t1 = new Todo(1,"text",false, earlierDate, 2, "home");
        t2 = new Todo(2, "text",false, laterDate, 2, "home");
        t3 = new Todo(1,"text",false, null, 2, "home");
    }

    @Test
    public void compare() {
        assertEquals(-2, c.compare(t1, t2));
        assertEquals(1, c.compare(t3, t2));
        assertEquals(-1, c.compare(t1, t3));
        assertEquals(0, c.compare(t3,t3));
    }
}