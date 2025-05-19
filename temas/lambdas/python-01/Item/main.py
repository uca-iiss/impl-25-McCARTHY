from Item import Item, build_equals_filter, build_greater_than_filter, build_less_than_filter, build_range_filter, build_contains_filter

# Ejemplo de uso
items = [
    Item("Laptop", "Electronics", 1200, 5),
    Item("Mouse", "Electronics", 25, 100),
    Item("Banana", "Groceries", 1, 200),
    Item("Shampoo", "Personal Care", 7, 50),
    Item("Apple", "Groceries", 2, 300),
    Item("Keyboard", "Electronics", 80, 30),
]

# Pruebas de los filtros
electronics_filter = build_equals_filter("category", "Electronics")
expensive_filter = build_greater_than_filter("price", 100)
cheap_filter = build_less_than_filter("price", 10)
range_filter = build_range_filter("price", 5, 50)
name_contains_filter = build_contains_filter("name", "a")

print("Electronics:", list(filter(electronics_filter, items)))
print("Expensive:", list(filter(expensive_filter, items)))
print("Cheap:", list(filter(cheap_filter, items)))
print("Price Range (5 to 50):", list(filter(range_filter, items)))
print("Name contains 'a':", list(filter(name_contains_filter, items)))
