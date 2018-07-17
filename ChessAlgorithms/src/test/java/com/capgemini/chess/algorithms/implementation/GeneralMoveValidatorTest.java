package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class GeneralMoveValidatorTest {

	@Test
	public void shouldReturnTrueIfMoveIsOnBoard() {
		// given
		GeneralMoveValidator myValidator = new GeneralMoveValidator();

		// when
		Coordinate from = (new Coordinate(7, 0));
		Coordinate to = (new Coordinate(2, 1));
		
		boolean exceptionThrown = true;
		try {
			myValidator.moveIsOnBoard(from, to);
		} catch (InvalidMoveException e) {
			exceptionThrown = false;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void shouldReturnFalseIfMoveIsNotOnBoard() {

		// given
		GeneralMoveValidator myValidator = new GeneralMoveValidator();

		// when
		Coordinate from = (new Coordinate(-1, 5));
		Coordinate to = (new Coordinate(2, 1));
		boolean exceptionThrown = true;
		try {
			myValidator.moveIsOnBoard(from, to);
		} catch (InvalidMoveException e) {
			exceptionThrown = false;
		}

		// then
		assertFalse(exceptionThrown);
	}
	
	/*@Test
	public void shouldReturnFalse() {

		// given
		GeneralMoveValidator myValidator = new GeneralMoveValidator();

		// when
		Coordinate from = (new Coordinate(-1, 5));
		Coordinate to = (new Coordinate(2, 1));
		
		myValidator.moveIsOnBoard(from, to);
		

		// then
		assertFalse(myValidator.);
	}*/
}
