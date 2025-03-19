// Generated from src\main\antlr4\com\minisoft\MiniSoft.g4 by ANTLR 4.10.1
package com.minisoft.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniSoftParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniSoftVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MiniSoftParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MiniSoftParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(MiniSoftParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MiniSoftParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayDeclaration(MiniSoftParser.ArrayDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#constantDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDeclaration(MiniSoftParser.ConstantDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MiniSoftParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MiniSoftParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(MiniSoftParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#doWhileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhileLoop(MiniSoftParser.DoWhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(MiniSoftParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#ioStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIoStatement(MiniSoftParser.IoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#inputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputStatement(MiniSoftParser.InputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#outputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutputStatement(MiniSoftParser.OutputStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orCondition}
	 * labeled alternative in {@link MiniSoftParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrCondition(MiniSoftParser.OrConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andCondition}
	 * labeled alternative in {@link MiniSoftParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCondition(MiniSoftParser.AndConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesizedCondition}
	 * labeled alternative in {@link MiniSoftParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedCondition(MiniSoftParser.ParenthesizedConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notCondition}
	 * labeled alternative in {@link MiniSoftParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotCondition(MiniSoftParser.NotConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparisonCondition}
	 * labeled alternative in {@link MiniSoftParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonCondition(MiniSoftParser.ComparisonConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccessExpr}
	 * labeled alternative in {@link MiniSoftParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpr(MiniSoftParser.ArrayAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negationExpr}
	 * labeled alternative in {@link MiniSoftParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationExpr(MiniSoftParser.NegationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link MiniSoftParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(MiniSoftParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link MiniSoftParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpr(MiniSoftParser.PrimaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link MiniSoftParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(MiniSoftParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link MiniSoftParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(MiniSoftParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MiniSoftParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(MiniSoftParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(MiniSoftParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniSoftParser#comparisonOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonOperator(MiniSoftParser.ComparisonOperatorContext ctx);
}