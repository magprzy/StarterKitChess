package com.capgemini.chess.algorithms.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
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

	@Test
	public void shouldReturnTrueIfSquareIsOcupated() throws InvalidMoveException {

		// given
		BoardManager boardManager = new BoardManager();
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7, 0));
		GeneralMoveValidator myValidator = new GeneralMoveValidator(boardManager);

		// when

		boolean result = myValidator.squareIsOccupied(new Coordinate(7, 0));

		// then
		assertTrue(result);
	}

	@Test
	public void shouldReturnFalseIfSquareIsEmpty() throws InvalidMoveException {

		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);

		board.setPieceAt(null, new Coordinate(4, 0));
		GeneralMoveValidator myValidator = new GeneralMoveValidator(boardManager);

		// when
		boolean exceptionThrown = true;
		try {
			myValidator.squareIsOccupied(new Coordinate(4, 0));
		} catch (InvalidMoveException e) {
			exceptionThrown = false;
		}

		// then
		assertFalse(exceptionThrown);
	}

	@Test
	public void shouldReturnTrueIfSquareFromIsDfferentThanTo() throws InvalidMoveException {

		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(7, 0));

		GeneralMoveValidator myValidator = new GeneralMoveValidator(boardManager);

		// when
		boolean exceptionThrown = true;
		try {
			myValidator.squareFromIsDfferentThanTo(new Coordinate(4, 0), new Coordinate(7, 0));
		} catch (InvalidMoveException e) {
			exceptionThrown = false;
		}

		// then
		assertTrue(exceptionThrown);
	}

	@Test
	public void shouldReturnFalseIfSquareFromIsEqualsTo() throws InvalidMoveException {

		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));

		GeneralMoveValidator myValidator = new GeneralMoveValidator(boardManager);

		// when
		boolean exceptionThrown = true;
		try {
			myValidator.squareFromIsDfferentThanTo(new Coordinate(4, 0), new Coordinate(4, 0));
		} catch (InvalidMoveException e) {
			exceptionThrown = false;
		}

		// then
		assertFalse(exceptionThrown);
	}

	@Test
	public void shouldReturnTypeKing() throws InvalidMoveException {

		// given
		Board board = new Board();
		BoardManager boardManager = new BoardManager(board);
		board.setPieceAt(Piece.WHITE_KING, new Coordinate(4, 0));
		GeneralMoveValidator myValidator = new GeneralMoveValidator(boardManager);

		// when
		PieceType pieceType = myValidator.pieceTypeOnSquare(new Coordinate(4, 0));

		// then
		assertEquals(PieceType.KING, pieceType);
	}

	@Test
	public void twoMoveCheckmate() throws InvalidMoveException {
		// given
		BoardManager boardManager = new BoardManager();
		boardManager.performMove(new Coordinate(6, 1), new Coordinate(6, 3));
		boardManager.performMove(new Coordinate(4, 6), new Coordinate(4, 4));
		boardManager.performMove(new Coordinate(5, 1), new Coordinate(5, 2));
		Move move = boardManager.performMove(new Coordinate(3, 7), new Coordinate(7, 3));
		BoardState boardState = boardManager.updateBoardState();

		assertEquals(MoveType.ATTACK, move.getType());
		assertEquals(Piece.BLACK_QUEEN, move.getMovedPiece());

		// then
		assertEquals(BoardState.CHECK_MATE, boardState);

	}

	@Test
	public void testWhitePawn() {
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_PAWN, new Coordinate(0, 1));

		// when
		BoardManager boardManager = new BoardManager(board);
		boolean exceptionThrown = false;
		try {
			boardManager.performMove(new Coordinate(0, 1), new Coordinate(5, 1));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}

		// then
		assertTrue(exceptionThrown);

	}

	@Test
	public void shouldRetureTrueIfRookCanMoveBack() throws InvalidMoveException {
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(2, 4));

		// when
		BoardManager boardManager = new BoardManager(board);

		Move move = boardManager.performMove(new Coordinate(2, 4), new Coordinate(2, 0));
		Piece newCoordinate = board.getPieceAt(new Coordinate(2, 0));

		// then
		assertEquals(Piece.WHITE_ROOK, move.getMovedPiece());
		assertEquals(Piece.WHITE_ROOK, newCoordinate);

	}

	@Test
	public void shouldRetureTrueIfRookCanMoveInLeft() throws InvalidMoveException {
		Board board = new Board();
		board.setPieceAt(Piece.WHITE_ROOK, new Coordinate(6, 5));

		// when
		BoardManager boardManager = new BoardManager(board);

		Move move = boardManager.performMove(new Coordinate(6, 5), new Coordinate(2, 5));
		Piece newCoordinate = board.getPieceAt(new Coordinate(2, 5));

		// then
		assertEquals(Piece.WHITE_ROOK, move.getMovedPiece());
		assertEquals(Piece.WHITE_ROOK, newCoordinate);
	}

}
