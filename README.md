# software_testing_assignment6

For Part One of Assignment 6, I created 8 tests in total. 5 of those tests were specification based and 3 of them were structural based.

The partitions I identified for the BarnesAndNoble class for the getPriceForCart method were the map is null, the map is empty, the map contians one valid order,
the map contains one order that does not exist in the database, and the map contains multiple orders. Some of those orders' books may have enough quantity and some may not.

The first test I created was called testOrderIsNull. This test simply tests if the getPriceForCart method functions correctly for the BarnesAndNoble class when the map, order,
that the user inputs is null. This test correctly identified that getPriceForCart returns null when the input is null.

The second test I created was called testorderIsEmpty. This test tests if the getPriceForCart method functions correctly and returns a PurchaseSummary object whose 
totalPrice value is zero, and Unavailable map is empty. This test passed.

The third test I created was called testOrderHasOneValidAllQuantity. This test makes sure if the getPriceFOrCart method accepts a map with only one valid book in it, it 
correctly returns the PurchaseSummary object with the correct totalPrice and Unavailable map. This test passed.

The fourth test I created was called testOrderBookNotInDatabase. This function tests to make sure that if the database returns null because the book could not be found, the 
method thrown a NullPointerException. This test passed.

The fifth and final test I created based solely on specification was testOrderHasMultipleValidAndNotEnoughQuantity. For this function, I made the order have 
multiple valid books that could be found in the database. However, the quantity inputted through the order somtimes exceeded the quantity of that book in the database. 
I checked the PurchaseSummary object returned by the function to ensure the correct totalValue and Unavailable map were returned. This test passed.

I created three more tests based solely on the structure of the BarnesAndNoble class. Since specification based testing is supposed to be a form of black box testing, I 
did not try to test anything specific within the private retrieveBook method that is used by the getPriceForCart method. Now, since structural based testing is white box,
I analyzed areas of the retrieveBook method that could be tested through the getPriceForCart method and wrote tests for them.

The areas that I found within the retrieveBook method that could be tested were if the ISBN was null, if the ISBN was an empty string, if the quantity requested by the 
user was negative, if the quantity requested by the user was zero, if the quantity requested was less than the quantity the book had, if the quantity requested was equal 
to the book quantity, and if the quantity requested was more than the book quantity.

I found that I had already tested a few of these partitions previously in my specification based testing (quantity requested was more and if the quantity requested was less).
And since the database was mocked, I did not think it would be a good idea to test if the ISBN was null since I did not have knowledge of the internal implementation of the 
database. 

The first test I created based on structure was testISBNIsEmptyString. This tested if the getPriceForCart function could still identify a book properly in the database
if the ISBN was an empty string. This test passed.

The second test I created based on structure was the testIfQuantityInputtedIsNeg. This tested if the getPriceForCart function threw an IllegalArgumentException when a user 
tried to input a negative quantity. I found that the function did not through an exception and instead returned a negative price. This test did not pass.

The third test I wrote was testIfQuantityInputtedIsZero. This test tested if the getPriceForCart function could correctly handle a user inputting a zero for the quantity
they wanted to order. The function returned a zero for the price. This test passed. 

