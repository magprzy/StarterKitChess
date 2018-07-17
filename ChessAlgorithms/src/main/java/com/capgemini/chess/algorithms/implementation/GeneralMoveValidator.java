package com.capgemini.chess.algorithms.implementation;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class GeneralMoveValidator {
	BoardManager boardManager = new BoardManager();
	Board board = boardManager.getBoard();
	
	
	public GeneralMoveValidator() {
		
	}
			
	public boolean checkInRange(int checked) {
		if(checked >= 0 && checked < 8){
			return true;
		}
		return false;	
	}
	
	public boolean moveIsOnBoard(Coordinate from, Coordinate to) throws InvalidMoveException{
		if ((checkInRange(from.getX()) == true) && (checkInRange(from.getY()) == true) 
				&& (checkInRange(to.getX()) == true) && (checkInRange(to.getY())== true))
		{
			return true;
		}			
		throw new InvalidMoveException();
	}
	
	/*public boolean moveIsOnBoard2(Coordinate from, Coordinate to) throws InvalidMoveException{
		if(from.getX()>=0)
		{
			return true;
		}			
		throw new InvalidMoveException();
	}*/
	
	
	public PieceType pieceTypeOnSquare(Coordinate from) {
		Piece piece = this.board.getPieceAt(from);
		PieceType pieceType = piece.getType();
		return pieceType;
	}

	public boolean squareIsOccupied(Coordinate from) throws InvalidMoveException {
		Piece piece = this.board.getPieceAt(from);
		if (piece != null) {
			return true;
		}
		throw new InvalidMoveException();
	}

}
