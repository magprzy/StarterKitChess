package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

import javafx.scene.shape.CubicCurve;

public class GeneralMoveValidator {
	BoardManager boardManager;
	Color playerColor;

	public GeneralMoveValidator() {

	}

	public GeneralMoveValidator(BoardManager boardManager) {
		this.boardManager = boardManager;
	}

	public boolean squareIsInRange(int x, int y) {
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			return true;
		}

		return false;
	}

	public boolean moveIsOnBoard(Coordinate from, Coordinate to) throws InvalidMoveException {
		if ((squareIsInRange(from.getX(), from.getY()) == true) && (squareIsInRange(to.getX(), to.getY()) == true)) {
			return true;
		}
		throw new InvalidMoveException();
	}

	public boolean squareIsOccupied(Coordinate from) throws InvalidMoveException {
		Board board = boardManager.getBoard();
		Piece piece = board.getPieceAt(from);
		if (piece != null) {
			return true;
		}
		throw new InvalidMoveException();
	}

	public boolean squareFromIsDfferentThanTo(Coordinate from, Coordinate to) throws InvalidMoveException {
		int xFrom = from.getX();
		int yFrom = from.getY();
		int xTo = to.getX();
		int yTo = to.getY();

		if (xFrom != xTo || yFrom != yTo) {
			return true;
		} else
			throw new InvalidMoveException();
	}

	/*
	 * public boolean squareIsOccupiedByOpponent(Coordinate to) throws
	 * InvalidMoveException { Board board = boardManager.getBoard(); Piece piece
	 * = board.getPieceAt(to); if (piece != null) { return true; } else return
	 * false; }
	 */
	
	public PieceType pieceTypeOnSquare(Coordinate from) {
		Board board = boardManager.getBoard();
		Piece piece = board.getPieceAt(from);
		PieceType pieceType = piece.getType();
		return pieceType;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	public boolean onSquareFromIsPlayerPiece(Coordinate from) throws InvalidMoveException {
		
		Board board = boardManager.getBoard();
		Piece piece = board.getPieceAt(from);
		Color pieceColor = piece.getColor();

		if (pieceColor.equals(playerColor)) {
			return true;
		}
		else
		{throw new InvalidMoveException();}

	}

	public boolean onSquareToIsNotPlayerPiece(Coordinate to) throws InvalidMoveException {
		Board board = boardManager.getBoard();
		Piece piece = board.getPieceAt(to);
		Color pieceColor = piece.getColor();

		if (pieceColor.equals(playerColor)) {
			throw new InvalidMoveException();
		} else
			return true;

	}

	public List<Coordinate> generatesAllPosibleMovesFromTo(Coordinate from, Coordinate to) throws InvalidMoveException {
		GeneralMoveValidator moveValidator = new GeneralMoveValidator(boardManager);
		List<Coordinate> allMovesFromTo = new ArrayList<Coordinate>();
		MoveController moveController = new MoveController(boardManager);
		PieceType pieceType = moveValidator.pieceTypeOnSquare(from);
		switch (pieceType) {
		case ROOK:
			allMovesFromTo = moveController.rookMove(from, to);
			break;
		case BISHOP:
			allMovesFromTo = moveController.bishopMove(from, to);
			break;
		case QUEEN:
			allMovesFromTo = moveController.queenMove(from, to);
			break;
		case KING:
			allMovesFromTo = moveController.kingMove(from, to);
			break;
		case PAWN:
			allMovesFromTo = moveController.pawnMove(from, to);
			break;
		case KNIGHT:
			allMovesFromTo = moveController.knightMove(from, to);
			break;
		default:
			break;
		}
		return allMovesFromTo;
	}

	public Move makeMove(List<Coordinate> allMovesFromTo, Coordinate from, Coordinate to) throws InvalidMoveException {
		Move move = new Move();
		Board board = boardManager.getBoard();
		Piece pieceFrom = board.getPieceAt(from);
		Piece pieceTo = board.getPieceAt(to);
		int full = 0;

		if (allMovesFromTo.size() == 1 && pieceFrom.getType() != PieceType.PAWN) {
			if (pieceTo == null) {
				move.setType(MoveType.ATTACK);
				board.setPieceAt(pieceFrom, to);
			} else {
				move.setType(MoveType.CAPTURE);
				board.setPieceAt(pieceFrom, to);
			}
		} 
		else if (allMovesFromTo.size() > 1 && pieceFrom.getType() != PieceType.PAWN) {
			allMovesFromTo.remove(to);
			for (int i = 0; i < allMovesFromTo.size(); i++) {

				Coordinate currentCoordinate = allMovesFromTo.get(i);
				Piece currentPiece = board.getPieceAt(currentCoordinate);
				if (currentPiece != null) {
					full = +1;
				}
			}
			if (full == 0) {
				if (pieceTo == null) {
					move.setType(MoveType.ATTACK);
					board.setPieceAt(pieceFrom, to);
				} else {
					move.setType(MoveType.CAPTURE);
					board.setPieceAt(pieceFrom, to);
				}
			}
			else 
				throw new InvalidMoveException();
		}
		else if((allMovesFromTo.size() == 1 && pieceFrom.getType() == PieceType.PAWN)){
			if(from.getX() == to.getX()){
				if (pieceTo == null) {
					move.setType(MoveType.ATTACK);
					board.setPieceAt(pieceFrom, to);
					}
				else
					throw new InvalidMoveException();
				}
			else if(from.getX() != to.getX()){
				if (pieceTo != null) {
					move.setType(MoveType.CAPTURE);
					board.setPieceAt(pieceFrom, to);
					}
				else
					throw new InvalidMoveException();
				}
			else if((allMovesFromTo.size() > 1 && pieceFrom.getType() == PieceType.PAWN)){
				if (pieceTo == null){
					allMovesFromTo.remove(to);
					Coordinate squareBetweenFromTo = allMovesFromTo.get(0);
					Piece pieceBetweenFromTo = board.getPieceAt(squareBetweenFromTo);	
					if(pieceBetweenFromTo == null){
						move.setType(MoveType.ATTACK);
						board.setPieceAt(pieceFrom, to);
					}
				}
				else 
					throw new InvalidMoveException();
			}
				
				
				
			}
		move.setFrom(from);
		move.setTo(to);
		move.setMovedPiece(pieceFrom);
		return move;
		}
	

	public boolean squareIsFree(Coordinate to) {

		Board board = boardManager.getBoard();
		Piece piece = board.getPieceAt(to);
		if (piece == null) {
			return true;
		}
		return false;
	}

}
