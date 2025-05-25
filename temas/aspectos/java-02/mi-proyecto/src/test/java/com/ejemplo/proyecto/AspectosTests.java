package com.ejemplo.proyecto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class AspectosTests {

    private BookManager bookManager;

    @BeforeEach
    void setUp() {
        bookManager = new BookManager();
    }

    @Test
    @DisplayName("Test creación y métodos de Book")
    public void testBookCreationAndMethods() {
        // Crear un libro
        Book book = new Book("El Quijote", "123456789");
        
        // Verificar getters
        assertEquals("El Quijote", book.getTitle());
        assertEquals("123456789", book.getIsbn());
        
        // Verificar disponibilidad inicial
        assertTrue(book.isAvailable());
        
        // Verificar préstamo
        book.borrow();
        assertFalse(book.isAvailable());
        
        // Verificar devolución
        book.returnBook();
        assertTrue(book.isAvailable());
    }
    
    @Test
    @DisplayName("Test creación y métodos de Member")
    public void testMemberCreationAndMethods() {
        // Crear un miembro
        Member member = new Member("Juan Pérez", "M001");
        
        // Verificar getters
        assertEquals("Juan Pérez", member.getName());
        assertEquals("M001", member.getId());
        
        // Verificar lista de libros prestados (vacía inicialmente)
        assertTrue(member.getBorrowedBooks().isEmpty());
        
        // Crear y prestar un libro
        Book book = new Book("El Quijote", "123456789");
        member.borrowBook(book);
        
        // Verificar que el libro se agregó a la lista y cambió su estado
        assertEquals(1, member.getBorrowedBooks().size());
        assertFalse(book.isAvailable());
        
        // Devolver el libro
        member.returnBook(book);
        
        // Verificar que el libro se eliminó de la lista y cambió su estado
        assertTrue(member.getBorrowedBooks().isEmpty());
        assertTrue(book.isAvailable());
    }
    
    @Test
    @DisplayName("Test añadir y buscar libros en BookManager")
    public void testAddAndFindBooks() {
        // Crear y añadir libros
        Book book1 = new Book("El Quijote", "123456789");
        Book book2 = new Book("Cien años de soledad", "987654321");
        
        bookManager.addBook(book1);
        bookManager.addBook(book2);
        
        // Buscar libro por ISBN
        Book foundBook = bookManager.findBookByIsbn("123456789");
        assertNotNull(foundBook);
        assertEquals("El Quijote", foundBook.getTitle());
        
        // Buscar libro que no existe
        Book notFoundBook = bookManager.findBookByIsbn("000000000");
        assertNull(notFoundBook);
        
        // Verificar libros disponibles
        List<Book> availableBooks = bookManager.getAvailableBooks();
        assertEquals(2, availableBooks.size());
    }
    
    @Test
    @DisplayName("Test registro y búsqueda de miembros en BookManager")
    public void testRegisterAndFindMembers() {
        // Crear y registrar miembros
        Member member1 = new Member("Juan Pérez", "M001");
        Member member2 = new Member("Ana García", "M002");
        
        bookManager.registerMember(member1);
        bookManager.registerMember(member2);
        
        // Buscar miembro por ID
        Member foundMember = bookManager.findMemberById("M001");
        assertNotNull(foundMember);
        assertEquals("Juan Pérez", foundMember.getName());
        
        // Buscar miembro que no existe
        Member notFoundMember = bookManager.findMemberById("M999");
        assertNull(notFoundMember);
    }
    
    @Test
    @DisplayName("Test préstamo y devolución de libros en BookManager")
    public void testLendAndReturnBooks() {
        // Crear y añadir libros
        Book book1 = new Book("El Quijote", "123456789");
        Book book2 = new Book("Cien años de soledad", "987654321");
        bookManager.addBook(book1);
        bookManager.addBook(book2);
        
        // Crear y registrar miembro
        Member member = new Member("Juan Pérez", "M001");
        bookManager.registerMember(member);
        
        // Prestar libro exitosamente
        boolean lendResult = bookManager.lendBook("M001", "123456789");
        assertTrue(lendResult);
        assertFalse(book1.isAvailable());
        assertEquals(1, member.getBorrowedBooks().size());
        
        // Intentar prestar un libro ya prestado
        lendResult = bookManager.lendBook("M001", "123456789");
        assertFalse(lendResult);
        
        // Verificar libros disponibles después del préstamo
        List<Book> availableBooks = bookManager.getAvailableBooks();
        assertEquals(1, availableBooks.size());
        assertEquals("Cien años de soledad", availableBooks.get(0).getTitle());
        
        // Devolver libro exitosamente
        boolean returnResult = bookManager.returnBook("M001", "123456789");
        assertTrue(returnResult);
        assertTrue(book1.isAvailable());
        assertEquals(0, member.getBorrowedBooks().size());
        
        // Intentar devolver un libro que no está prestado
        returnResult = bookManager.returnBook("M001", "123456789");
        assertFalse(returnResult);
        
        // Verificar libros disponibles después de la devolución
        availableBooks = bookManager.getAvailableBooks();
        assertEquals(2, availableBooks.size());
    }
    
    @Test
    @DisplayName("Test casos especiales de préstamo y devolución")
    public void testSpecialCasesLendAndReturn() {
        // Crear y añadir libro
        Book book = new Book("El Quijote", "123456789");
        bookManager.addBook(book);
        
        // Crear y registrar miembro
        Member member = new Member("Juan Pérez", "M001");
        bookManager.registerMember(member);
        
        // Intentar prestar con ID de miembro inválido
        boolean lendResult = bookManager.lendBook("INVALID", "123456789");
        assertFalse(lendResult);
        assertTrue(book.isAvailable());
        
        // Intentar prestar con ISBN inválido
        lendResult = bookManager.lendBook("M001", "INVALID");
        assertFalse(lendResult);
        assertTrue(book.isAvailable());
        
        // Prestar correctamente para probar devoluciones
        lendResult = bookManager.lendBook("M001", "123456789");
        assertTrue(lendResult);
        
        // Intentar devolver con ID de miembro inválido
        boolean returnResult = bookManager.returnBook("INVALID", "123456789");
        assertFalse(returnResult);
        assertFalse(book.isAvailable());
        
        // Intentar devolver con ISBN inválido
        returnResult = bookManager.returnBook("M001", "INVALID");
        assertFalse(returnResult);
        assertFalse(book.isAvailable());
    }
}