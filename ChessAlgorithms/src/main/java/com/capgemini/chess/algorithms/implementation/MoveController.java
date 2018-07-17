package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;

public class MoveController {
	BoardManager boardManager = new BoardManager();
	Board board = boardManager.getBoard();

	public MoveController() {

	}

	public List<Coordinate> rookMove(Coordinate coordinate) {
		List<Coordinate> moves = new ArrayList<Coordinate>();

		int x = coordinate.getX();
		int y = coordinate.getY();

		for (int i = 0; i < 8; i++) {
			moves.add(new Coordinate(i, y));
		}
		for (int i = 0; i < 8; i++) {
			moves.add(new Coordinate(x, i));
		}
		moves.remove(coordinate);
		moves.remove(coordinate);

		return moves;

	}

	public List<Coordinate> bishopMove(Coordinate coordinate) {
		List<Coordinate> moves = new ArrayList<Coordinate>();

		int x = coordinate.getX();
		int y = coordinate.getY();

		for (int i = 1; i < 8; i++) {
			moves.add(new Coordinate(x + i, y + i));
		}
		for (int i = 1; i < 8; i++) {
			moves.add(new Coordinate(x + i, y - i));
		}
		for (int i = 1; i < 8; i++) {
			moves.add(new Coordinate(x - i, y - i));
		}
		for (int i = 1; i < 8; i++) {
			moves.add(new Coordinate(x - i, y + i));
		}

		return moves;
	}

	public List<Coordinate> queenMove(Coordinate coordinate) {

		List<Coordinate> rookMoves = rookMove(coordinate);
		List<Coordinate> bishopMoves = bishopMove(coordinate);

		rookMoves.addAll(bishopMoves);
		List<Coordinate> queenMoves = rookMoves;

		return queenMoves;
	}

	public List<Coordinate> kingMove(Coordinate coordinate) {
		List<Coordinate> kingMoves = new ArrayList<Coordinate>();

		int x = coordinate.getX();
		int y = coordinate.getY();

		kingMoves.add(new Coordinate(x + 1, y));
		kingMoves.add(new Coordinate(x + 1, y + 1));
		kingMoves.add(new Coordinate(x, y + 1));
		kingMoves.add(new Coordinate(x - 1, y + 1));
		kingMoves.add(new Coordinate(x - 1, y));
		kingMoves.add(new Coordinate(x - 1, y - 1));
		kingMoves.add(new Coordinate(x, y - 1));
		kingMoves.add(new Coordinate(x + 1, y - 1));

		return kingMoves;
	}

	public List<Coordinate> pawnMove(Coordinate coordinate) {
		List<Coordinate> pawnMoves = new ArrayList<Coordinate>();

		int x = coordinate.getX();
		int y = coordinate.getY();

		Piece piece = this.board.getPieceAt(coordinate);
		Color pieceColor = piece.getColor();

		if (pieceColor == Color.WHITE) {
			if (y == 1) {
				pawnMoves.add(new Coordinate(x, y + 1));
				pawnMoves.add(new Coordinate(x, y + 2));
				pawnMoves.add(new Coordinate(x - 1, y + 1));
				pawnMoves.add(new Coordinate(x + 1, y + 1));
			} else {
				pawnMoves.add(new Coordinate(x, y + 1));
				pawnMoves.add(new Coordinate(x - 1, y + 1));
				pawnMoves.add(new Coordinate(x + 1, y + 1));

			}
		} else if (pieceColor == Color.BLACK) {

			if (y == 6) {
				pawnMoves.add(new Coordinate(x, y - 1));
				pawnMoves.add(new Coordinate(x, y - 2));
				pawnMoves.add(new Coordinate(x - 1, y - 1));
				pawnMoves.add(new Coordinate(x + 1, y - 1));
			} else {
				pawnMoves.add(new Coordinate(x, y - 1));
				pawnMoves.add(new Coordinate(x - 1, y - 1));
				pawnMoves.add(new Coordinate(x + 1, y - 1));
			}
		}
		return pawnMoves;
	}

	public List<Coordinate> knightMove(Coordinate coordinate) {
		List<Coordinate> knightMoves = new ArrayList<Coordinate>();

		int x = coordinate.getX();
		int y = coordinate.getY();

		knightMoves.add(new Coordinate(x + 1, y + 2));
		knightMoves.add(new Coordinate(x + 2, y + 1));
		knightMoves.add(new Coordinate(x + 2, y - 1));
		knightMoves.add(new Coordinate(x + 1, y - 2));
		knightMoves.add(new Coordinate(x - 1, y + 2));
		knightMoves.add(new Coordinate(x - 2, y + 1));
		knightMoves.add(new Coordinate(x - 2, y - 1));
		knightMoves.add(new Coordinate(x - 1, y - 2));

		return knightMoves;
	}
}
