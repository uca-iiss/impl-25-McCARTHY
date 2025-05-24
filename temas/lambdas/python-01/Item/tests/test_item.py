import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..')))

from Item.Item import Item, build_equals_filter, build_greater_than_filter, build_less_than_filter, build_range_filter, build_contains_filter
import pytest

@pytest.fixture
def items():
    return [
        Item("Laptop", "Electronics", 1200, 5),
        Item("Mouse", "Electronics", 25, 100),
        Item("Banana", "Groceries", 1, 200),
        Item("Shampoo", "Personal Care", 7, 50),
        Item("Apple", "Groceries", 2, 300),
        Item("Keyboard", "Electronics", 80, 30),
    ]

def test_build_equals_filter(items):
    electronics_filter = build_equals_filter("category", "Electronics")
    filtered_items = list(filter(electronics_filter, items))
    assert len(filtered_items) == 3
    assert all(item.category == "Electronics" for item in filtered_items)

def test_build_greater_than_filter(items):
    expensive_filter = build_greater_than_filter("price", 100)
    filtered_items = list(filter(expensive_filter, items))
    assert len(filtered_items) == 1
    assert filtered_items[0].name == "Laptop"

def test_build_less_than_filter(items):
    cheap_filter = build_less_than_filter("price", 10)
    filtered_items = list(filter(cheap_filter, items))
    assert len(filtered_items) == 3
    assert {item.name for item in filtered_items} == {"Banana", "Apple", "Shampoo"}

def test_build_range_filter(items):
    range_filter = build_range_filter("price", 5, 50)
    filtered_items = list(filter(range_filter, items))
    assert len(filtered_items) == 2
    assert {item.name for item in filtered_items} == {"Shampoo", "Mouse"}

def test_build_contains_filter(items):
    name_contains_filter = build_contains_filter("name", "a")
    filtered_items = list(filter(name_contains_filter, items))
    assert len(filtered_items) == 4
    assert {item.name for item in filtered_items} == {"Banana", "Shampoo", "Keyboard", "Laptop"}
