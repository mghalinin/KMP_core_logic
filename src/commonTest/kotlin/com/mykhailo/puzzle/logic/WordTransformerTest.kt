package com.mykhailo.puzzle.logic

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WordTransformerTest {
    
    @Test
    fun testDeleteLetter() {
        assertEquals("ello", WordTransformer.deleteLetter("hello", 0))
        assertEquals("hllo", WordTransformer.deleteLetter("hello", 1))
        assertEquals("hell", WordTransformer.deleteLetter("hello", 4))
        assertEquals("", WordTransformer.deleteLetter("a", 0))
    }
    
    @Test
    fun testDeleteLetterInvalidIndex() {
        assertFailsWith<IndexOutOfBoundsException> {
            WordTransformer.deleteLetter("hello", -1)
        }
        assertFailsWith<IndexOutOfBoundsException> {
            WordTransformer.deleteLetter("hello", 5)
        }
    }
    
    @Test
    fun testMoveLetter() {
        assertEquals("ehllo", WordTransformer.moveLetter("hello", 1, 0))
        assertEquals("hlelo", WordTransformer.moveLetter("hello", 1, 2))
        assertEquals("hellо", WordTransformer.moveLetter("hello", 0, 4))
        assertEquals("hello", WordTransformer.moveLetter("hello", 2, 2))
    }
    
    @Test
    fun testMoveLetterInvalidIndex() {
        assertFailsWith<IndexOutOfBoundsException> {
            WordTransformer.moveLetter("hello", -1, 0)
        }
        assertFailsWith<IndexOutOfBoundsException> {
            WordTransformer.moveLetter("hello", 0, 5)
        }
    }
    
    @Test
    fun testExchangeLetter() {
        assertEquals("aello", WordTransformer.exchangeLetter("hello", 0, 'a'))
        assertEquals("hxllo", WordTransformer.exchangeLetter("hello", 1, 'x'))
        assertEquals("hellz", WordTransformer.exchangeLetter("hello", 4, 'z'))
    }
    
    @Test
    fun testExchangeLetterInvalidIndex() {
        assertFailsWith<IndexOutOfBoundsException> {
            WordTransformer.exchangeLetter("hello", -1, 'a')
        }
        assertFailsWith<IndexOutOfBoundsException> {
            WordTransformer.exchangeLetter("hello", 5, 'a')
        }
    }
    
    @Test
    fun testApplyMove() {
        val deleteMove = com.mykhailo.puzzle.model.Move.delete(0, 1)
        assertEquals("ello", WordTransformer.applyMove("hello", deleteMove))
        
        val moveMove = com.mykhailo.puzzle.model.Move.move(1, 0, 1)
        assertEquals("ehllo", WordTransformer.applyMove("hello", moveMove))
        
        val exchangeMove = com.mykhailo.puzzle.model.Move.exchange(0, 'a', 1)
        assertEquals("aello", WordTransformer.applyMove("hello", exchangeMove))
    }
}