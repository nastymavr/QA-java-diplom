package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;
    private Bun mockBun;
    private Ingredient mockSauce;
    private Ingredient mockFilling;

    @Parameterized.Parameter(0)
    public float bunPrice;
    @Parameterized.Parameter(1)
    public float saucePrice;
    @Parameterized.Parameter(2)
    public float fillingPrice;
    @Parameterized.Parameter(3)
    public float expectedPrice;

    @Parameterized.Parameters(name = "bun={0}, sauce={1}, filling={2} => total={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {100f, 50f, 150f, 400f},   // 2*100 + 50 + 150
                {200f, 0f, 100f, 500f},    // 2*200 + 0 + 100
                {50f, 25f, 25f, 150f},     // 2*50 + 25 + 25
        });
    }

    @Before
    public void setUp() {
        burger = new Burger();

        // моки булки и ингредиентов
        mockBun = mock(Bun.class);
        mockSauce = mock(Ingredient.class);
        mockFilling = mock(Ingredient.class);

        when(mockBun.getName()).thenReturn("test bun");
        when(mockBun.getPrice()).thenReturn(bunPrice);

        when(mockSauce.getType()).thenReturn(IngredientType.SAUCE);
        when(mockSauce.getName()).thenReturn("ketchup");
        when(mockSauce.getPrice()).thenReturn(saucePrice);

        when(mockFilling.getType()).thenReturn(IngredientType.FILLING);
        when(mockFilling.getName()).thenReturn("cutlet");
        when(mockFilling.getPrice()).thenReturn(fillingPrice);

        burger.setBuns(mockBun);
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
    }

    @Test
    public void testSetBuns() {
        assertEquals("test bun", burger.bun.getName());
    }

    @Test
    public void testAddIngredient() {
        assertEquals(2, burger.ingredients.size());
        assertEquals("ketchup", burger.ingredients.get(0).getName());
    }

    @Test
    public void testRemoveIngredient() {
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
        assertEquals("cutlet", burger.ingredients.get(0).getName());
    }

    @Test
    public void testMoveIngredient() {
        // перемещаем первый элемент в конец
        burger.moveIngredient(0, 1);
        assertEquals("ketchup", burger.ingredients.get(1).getName());
        assertEquals("cutlet", burger.ingredients.get(0).getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }
    @Test
    public void testEmptyBurgerPriceAndReceipt() {
        Burger emptyBurger = new Burger();
        emptyBurger.setBuns(mockBun); // только булка
        assertEquals(bunPrice * 2, emptyBurger.getPrice(), 0.001f);

        String receipt = emptyBurger.getReceipt();
        assertTrue(receipt.contains("(==== test bun ====)"));
        assertTrue(receipt.contains(String.format("Price: %f", emptyBurger.getPrice())));
    }

    @Test
    public void testGetReceipt() {
        String receipt = burger.getReceipt();

        // проверяем наличие булки в чеке
        assertTrue(receipt.contains("(==== test bun ====)"));

        // проверяем наличие ингредиентов (с учётом форматирования)
        assertTrue(receipt.contains("= sauce ketchup ="));
        assertTrue(receipt.contains("= filling cutlet ="));

        // проверяем, что итоговая цена тоже есть в чеке
        assertTrue(receipt.contains(String.format("Price: %f", burger.getPrice())));
    }
}
