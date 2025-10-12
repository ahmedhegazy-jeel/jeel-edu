package com.jeeleducation.lms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Book Activity entity for interactive book-based learning content.
 */
@Entity
@DiscriminatorValue("BOOK")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookActivity extends Activity {

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("pageNumber ASC")
    private List<BookPage> pages = new ArrayList<>();

    /**
     * Add page to book.
     *
     * @param page Page to add
     */
    public void addPage(BookPage page) {
        if (pages == null) {
            pages = new ArrayList<>();
        }
        pages.add(page);
        page.setBook(this);
    }

    /**
     * Get total page count.
     *
     * @return Number of pages
     */
    public int getPageCount() {
        return pages != null ? pages.size() : 0;
    }
}

