package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.Piece;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class MoveController {
	BoardManager boardManager;
	GeneralMoveValidator moveValidator = new GeneralMoveValidator(boardManager);
	
	public MoveController( ) {
		
	}
	public MoveController(BoardManager boardManager) {
		this.boardManager = boardManager;
	}

	public List<Coordinate> rookMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> rookMoves = new ArrayList<Coordinate>();

		int xFrom = from.getX();
		int yFrom = from.getY();
		int xTo = to.getX();
		int yTo = to.getY();

		if (xFrom == xTo)
		{	if(yFrom > yTo){
				for(int y = yFrom+1; y >= yTo; y--)
				{
						rookMoves.add(new Coordinate(xFrom, y));
				}
				}
			else 
			{
				for (int y = yFrom+1; y<=yTo; y++)
				{rookMoves.add(new Coordinate(xFrom, y));}
			}	
		}	
		else if(yFrom==yTo)
		{	if(xFrom>xTo)
			{
			for(int x = xFrom+1; x >= xTo; x--)
				{rookMoves.add(new Coordinate(x, yFrom));
			}}
			else
			{
				for(int x = xFrom+1; x <= xTo; x++)
				{rookMoves.add(new Coordinate(x, yFrom));
				}
			}}
		else {
			throw new InvalidMoveException();
		}
	
		return rookMoves;
	}

	


	public List<Coordinate> bishopMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> bishopMoves = new ArrayList<Coordinate>();

		int xFrom = from.getX();
		int yFrom = from.getY();
		int xTo = to.getX();
		int yTo = to.getY();

		
		if ( xFrom < xTo){
				if (yFrom < yTo){
					for (int i = 1; i <= (xTo-xFrom); i++)
						bishopMoves.add(new Coordinate(xFrom + i,yFrom + i));
				}
				else {
					for (int i = 1; i <= (xTo-xFrom); i++) 
						bishopMoves.add(new Coordinate(xFrom + i,yFrom - i));
					
				}
				
			}
		else if (xFrom > xTo){
				if(yFrom < yTo){
					for (int i = 1; i <= (xFrom -xTo); i++)
						bishopMoves.add(new Coordinate(xFrom - i,yFrom + i));
				}
				else {
					for (int i = 1; i <= (xFrom -xTo); i++) {
						bishopMoves.add(new Coordinate(xFrom - i,yFrom - i));
					}
				}
			}
		
			else {
				throw new InvalidMoveException();
			}
		
		
		if (bishopMoves.contains(to)){
			return bishopMoves;
		}
		else {
			throw new InvalidMoveException();
		}
	}

	public List<Coordinate> queenMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> queenMoves = new ArrayList<Coordinate>();
		
		boolean exceptionThrown = false;
		
		try {
			queenMoves = rookMove(from, to);
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		if (exceptionThrown==true)
		{
			queenMoves = bishopMove(from, to);
		}

		return queenMoves;
	}

	public List<Coordinate> kingMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> kingMoves = new ArrayList<Coordinate>();

		int xFrom = from.getX();
		int yFrom = from.getY();
		int xTo = to.getX();
		int yTo = to.getY();
		
		if(xFrom == xTo-1 || xFrom == xTo || xFrom == xTo+1 ) {
			if (yFrom == yTo-1 || yFrom == xTo || yFrom == yTo +1 )
			{
			kingMoves.add(new Coordinate(xTo, yTo));
			}
			else {
				throw new InvalidMoveException();
			}	
		}
		else {
			throw new InvalidMoveException();
		}

		return kingMoves;
	}

	public List<Coordinate> pawnMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> pawnMoves = new ArrayList<Coordinate>();
		Board board = boardManager.getBoard();

		int xFrom = from.getX();
		int yFrom = from.getY();
		int xTo = to.getX();
		int yTo = to.getY();

		Piece piece = board.getPieceAt(from);
		Color pieceColor = piece.getColor();

		if (pieceColor == Color.WHITE) {
			if (yFrom == 1) {
				if (xTo == xFrom && yTo == yFrom+2){
					while( yFrom<=yTo ){
						pawnMoves.add(new Coordinate(xTo, yFrom++));
					}}}
				
			else  if((xTo == xFrom-1 && yTo == yFrom+1) || (xTo == xFrom && yTo ==yFrom +1) || (xTo == xFrom && yTo == yFrom+2))
					{
						pawnMoves.add(new Coordinate(xTo, yTo));
						} 	
				
				else 
					throw new InvalidMoveException();
				}
						
		else if (pieceColor == Color.BLACK) {
			if (yFrom == 6) {
				if (xTo == xFrom && yTo == yFrom-2){
					while( yFrom>=yTo ){
						pawnMoves.add(new Coordinate(xTo, yFrom--));
					}}}
				
			else  if((xTo == xFrom-1 && yTo == yFrom-1) || (xTo == xFrom && yTo ==yFrom-1) || (xTo == xFrom && yTo == yFrom-2))
					{
						pawnMoves.add(new Coordinate(xTo, yTo));
						} 	
				
				else 
					throw new InvalidMoveException();
				}
	
	
		pawnMoves.remove(from);
		return pawnMoves;
	}

	public List<Coordinate> knightMove(Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> knightMoves = new ArrayList<Coordinate>();

		int xFrom = from.getX();
		int yFrom = from.getY();
		int xTo = to.getX();
		int yTo = to.getY();
		
		
		if (xTo == xFrom +1 && yTo == yFrom+2){
				knightMoves.add(to);
		}
		else if (xTo == xFrom +1 && yTo == yFrom-2){
			knightMoves.add(to);
		}
		else if (xTo == xFrom +2 && yTo == yFrom+1){
			knightMoves.add(to);
		}
		else if (xTo == xFrom +2 && yTo == yFrom-1){
			knightMoves.add(to);
		}
		else if (xTo == xFrom -1 && yTo == yFrom+2){
			knightMoves.add(to);
		}
		else if (xTo == xFrom -1 && yTo == yFrom-2){
			knightMoves.add(to);
		}	
		else if (xTo == xFrom -2 && yTo == yFrom+1){
			knightMoves.add(to);
		}
		else if (xTo == xFrom -2 && yTo == yFrom-1){
			knightMoves.add(to);
		}
		else {
			throw new InvalidMoveException();
		}			
		return knightMoves;
	}
}
