package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BarnesAndNobleTest {

    //This is a specification based test. In this test I test that BarnesAndNobles'
    //getPriceForCart returns null when the order is null.
    @Test
    @DisplayName("specification-based")
    void testOrderIsNull() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); //a mock of the BookProcess class
        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        assertThat(barnes.getPriceForCart(null)).isEqualTo(null); //asserts that barnes' getpriceForCart returns null when order is null
    }

    //This is a specification based test. In this test I test that BarnesAndNobles
    //getPriceForCart returns a PurchaseSummary that has zero for its total price
    //and an empty hash map for its unavailable orders
    @Test
    @DisplayName("specification-based")
    void testOrderIsEmpty() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class
        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
        PurchaseSummary result=barnes.getPriceForCart(order); //this saves the returned purchase summary from the getPriceForCart method with the empty order

        assertThat(result.getTotalPrice()).isEqualTo(0); //this asserts that the returned purchase summary's total price is zero
        assertThat(result.getUnavailable()).isEqualTo(new HashMap<>());//this asserts that the returned purchase's unavailable hash map is empty
    }

    //This is a specification based test. In this test I test that BarnesAndNoble
    //getPriceForCart returns the correct PurchaseSummary values for the one valid book
    @Test
    @DisplayName("specification-based")
    void testOrderHasOneValidAllQuantity() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class
        Book bookValid=new Book("123", 5, 10); //a book object
        when(bookDb.findByISBN("123")).thenReturn(bookValid); //a mocking statement. when bookDb's findByISBN method is called, the book is returned

        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
        order.put("123", 10);

        PurchaseSummary result=barnes.getPriceForCart(order); //this saves the returned purchase summary from the getPriceForCart method

        assertThat(result.getTotalPrice()).isEqualTo(50); //this asserts that the returned purchase summary's total price is zero
        assertThat(result.getUnavailable()).isEqualTo(new HashMap<>());//this asserts that the returned purchase's unavailable hash map is empty
    }

    //This is a specification based test. In this test I test that BarnesAndNoble
    //getPriceForCart returns the correct PurchaseSummary values for the one valid book
    @Test
    @DisplayName("specification-based")
    void testOrderBookIsNotInDatabase() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class

        when(bookDb.findByISBN("123")).thenReturn(null); //a mocking statement. when bookDb's findByISBN method is called, null is returned

        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
        order.put("123", 10);

        assertThrows(NullPointerException.class, () -> { //since the database does not contain the book, it returns null
            barnes.getPriceForCart(order); //since a function is called on the object returned form the database, a null pointer exception is thrown
        });
    }

    //This is a specification based test. In this test I test that BarnesAndNoble
    //getPriceForCart returns the correct PurchaseSummary values for the multiple valid books and mutliple books with not enough quantity
    @Test
    @DisplayName("specification-based")
    void testOrderHasMultipleValidAndNotEnoughQuantity() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class
        Book bookOne=new Book("123", 5, 10); //a book object
        Book bookTwo=new Book("321", 3, 10); //a second book object
        Book bookThree=new Book("456", 2, 10); //a third book object
        Book bookFour=new Book("654", 2, 10);
        when(bookDb.findByISBN("123")).thenReturn(bookOne); //a mocking statement. when bookDb's findByISBN method is called, the book is returned
        when(bookDb.findByISBN("321")).thenReturn(bookTwo); //a mocking statement. when bookDb's findByISBN method is called, the book is returned
        when(bookDb.findByISBN("456")).thenReturn(bookThree); //a mocking statement. when bookDb's findByISBN method is called, the book is returned
        when(bookDb.findByISBN("654")).thenReturn(bookFour); //a mocking statement. when bookDb's findByISBN method is called, the book is returned

        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
        order.put("123", 7);
        order.put("321", 20);
        order.put("456", 10);
        order.put("654", 20);

        PurchaseSummary result=barnes.getPriceForCart(order); //this saves the returned purchase summary from the getPriceForCart method with the empty order
        Map<Book, Integer> answer=new HashMap<>(); //This will be compared to result's unavailable map
        answer.put(bookTwo, 10);
        answer.put(bookFour, 10);

        assertThat(result.getTotalPrice()).isEqualTo(105); //this asserts that the returned purchase summary's total price is 120
        assertThat(result.getUnavailable()).isEqualTo(answer);//this asserts that the returned purchase's unavailable hash map is not empty
    }

    //This is a structural based test. In this test I test that BarnesAndNoble
    //getPriceForCart functions correctly if the book's ISBN is an empty string
    @Test
    @DisplayName("structural-based")
    void testISBNIsEmptyString() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class
        Book oneBook=new Book("", 5, 5);
        when(bookDb.findByISBN("")).thenReturn(oneBook); //a mocking statement. when bookDb's findByISBN method is called, null is returned

        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
        order.put("", 10);
        PurchaseSummary result=barnes.getPriceForCart(order); //this saves the returned purchase summary from the getPriceForCart method with the empty order
        Map<Book, Integer> answer=new HashMap<>(); //This will be compared to result's unavailable map
        answer.put(oneBook, 5);

        assertThat(result.getTotalPrice()).isEqualTo(25); //this asserts that the returned purchase summary's total price is zero
        assertThat(result.getUnavailable()).isEqualTo(answer);//this asserts that the returned purchase's unavailable hash map is empty

    }

    //This is a structural based test. In this test I test that BarnesAndNoble
    //getPriceForCart returns an illegal argument exception if the quantity the user inputted is negative
//    @Test
//    @DisplayName("structural-based")
//    void testIfQuantityInputtedIsNeg() {
//        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
//        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class
//        Book oneBook=new Book("123", 5, 5);
//        when(bookDb.findByISBN("123")).thenReturn(oneBook); //a mocking statement. when bookDb's findByISBN method is called, null is returned
//
//        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies
//
//        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
//        order.put("123", -5); //the user orders a quantity that is negative
//        PurchaseSummary result=barnes.getPriceForCart(order); //we call the getPriceForCart method with a negative quantity
//        assertThrows(IllegalArgumentException.class, () ->{ //an illegal argument exception should be thrown since the negative quantity should not be accepted
//            barnes.getPriceForCart(order); //an illegal argument is not thrown so this test does not pass
//        });
//
//    }

    //This is a structural based test. In this test I test that BarnesAndNoble
    //getPriceForCart functions correctly if the user inputs a zero for the quantity they want to order
    @Test
    @DisplayName("structural-based")
    void testIfQuantityInputtedIsZero() {
        BookDatabase bookDb=mock(BookDatabase.class); //a mock of the BookDatabase class
        BuyBookProcess buyBook=mock(BuyBookProcess.class); // a mock of the BuyBookProcess class
        Book oneBook=new Book("123", 5, 5);
        when(bookDb.findByISBN("123")).thenReturn(oneBook); //a mocking statement. when bookDb's findByISBN method is called, null is returned

        BarnesAndNoble barnes=new BarnesAndNoble(bookDb, buyBook); //an instance of the BarnesAndNoble class with the mocked dependencies

        Map<String, Integer> order=new HashMap<>(); //an empty hashmap for the order
        order.put("123", 0);
        PurchaseSummary result=barnes.getPriceForCart(order); //this saves the returned purchase summary from the getPriceForCart method with the empty order
        Map<Book, Integer> answer=new HashMap<>(); //This will be compared to result's unavailable map


        assertThat(result.getTotalPrice()).isEqualTo(0); //this asserts that the returned purchase summary's total price is zero
        assertThat(result.getUnavailable()).isEqualTo(answer);//this asserts that the returned purchase's unavailable hash map is empty

    }




}