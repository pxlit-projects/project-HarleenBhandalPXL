package be.pxl.services.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostStatusTests {
    @Test
    public void testPostStatusValues() {
        PostStatus[] expectedValues = {PostStatus.CONCEPT, PostStatus.APPROVED, PostStatus.PENDING, PostStatus.REJECTED};
        assertArrayEquals(expectedValues, PostStatus.values());
    }

    @Test
    public void testPostStatusValueOf() {
        assertEquals(PostStatus.CONCEPT, PostStatus.valueOf("CONCEPT"));
        assertEquals(PostStatus.APPROVED, PostStatus.valueOf("APPROVED"));
        assertEquals(PostStatus.PENDING, PostStatus.valueOf("PENDING"));
        assertEquals(PostStatus.REJECTED, PostStatus.valueOf("REJECTED"));
    }
}
