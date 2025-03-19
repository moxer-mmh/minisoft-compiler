// Generated from src\main\antlr4\com\minisoft\MiniSoft.g4 by ANTLR 4.10.1
package com.minisoft.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniSoftParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, LET=23, DEFINE=24, CONST=25, 
		IF=26, THEN=27, ELSE=28, DO=29, WHILE=30, FOR=31, FROM=32, TO=33, STEP=34, 
		INPUT=35, OUTPUT=36, INT=37, FLOAT_TYPE=38, AND=39, OR=40, INTEGER=41, 
		FLOAT=42, IDENTIFIER=43, STRING=44, SINGLE_LINE_COMMENT=45, MULTI_LINE_COMMENT=46, 
		WS=47;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_declaration = 2, RULE_variableDeclaration = 3, 
		RULE_arrayDeclaration = 4, RULE_constantDeclaration = 5, RULE_assignment = 6, 
		RULE_ifStatement = 7, RULE_loopStatement = 8, RULE_doWhileLoop = 9, RULE_forLoop = 10, 
		RULE_ioStatement = 11, RULE_inputStatement = 12, RULE_outputStatement = 13, 
		RULE_condition = 14, RULE_expression = 15, RULE_primary = 16, RULE_identifier = 17, 
		RULE_dataType = 18, RULE_comparisonOperator = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "declaration", "variableDeclaration", "arrayDeclaration", 
			"constantDeclaration", "assignment", "ifStatement", "loopStatement", 
			"doWhileLoop", "forLoop", "ioStatement", "inputStatement", "outputStatement", 
			"condition", "expression", "primary", "identifier", "dataType", "comparisonOperator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "';'", "'['", "']'", "'='", "':='", "'('", "')'", "'{'", 
			"'}'", "','", "'!'", "'-'", "'*'", "'/'", "'+'", "'<'", "'>'", "'>='", 
			"'<='", "'=='", "'!='", "'let'", "'@define'", "'Const'", "'if'", "'then'", 
			"'else'", "'do'", "'while'", "'for'", "'from'", "'to'", "'step'", "'input'", 
			"'output'", "'Int'", "'Float'", "'AND'", "'OR'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "LET", 
			"DEFINE", "CONST", "IF", "THEN", "ELSE", "DO", "WHILE", "FOR", "FROM", 
			"TO", "STEP", "INPUT", "OUTPUT", "INT", "FLOAT_TYPE", "AND", "OR", "INTEGER", 
			"FLOAT", "IDENTIFIER", "STRING", "SINGLE_LINE_COMMENT", "MULTI_LINE_COMMENT", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniSoft.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniSoftParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MiniSoftParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LET) | (1L << DEFINE) | (1L << IF) | (1L << DO) | (1L << FOR) | (1L << INPUT) | (1L << OUTPUT) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(40);
				statement();
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(46);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public LoopStatementContext loopStatement() {
			return getRuleContext(LoopStatementContext.class,0);
		}
		public IoStatementContext ioStatement() {
			return getRuleContext(IoStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(53);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LET:
			case DEFINE:
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				declaration();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(49);
				assignment();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(50);
				ifStatement();
				}
				break;
			case DO:
			case FOR:
				enterOuterAlt(_localctx, 4);
				{
				setState(51);
				loopStatement();
				}
				break;
			case INPUT:
			case OUTPUT:
				enterOuterAlt(_localctx, 5);
				{
				setState(52);
				ioStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ArrayDeclarationContext arrayDeclaration() {
			return getRuleContext(ArrayDeclarationContext.class,0);
		}
		public ConstantDeclarationContext constantDeclaration() {
			return getRuleContext(ConstantDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		try {
			setState(58);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				variableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				arrayDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(57);
				constantDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TerminalNode LET() { return getToken(MiniSoftParser.LET, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(LET);
			setState(61);
			identifier();
			setState(62);
			match(T__0);
			setState(63);
			dataType();
			setState(64);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayDeclarationContext extends ParserRuleContext {
		public TerminalNode LET() { return getToken(MiniSoftParser.LET, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public TerminalNode INTEGER() { return getToken(MiniSoftParser.INTEGER, 0); }
		public ArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitArrayDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayDeclarationContext arrayDeclaration() throws RecognitionException {
		ArrayDeclarationContext _localctx = new ArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arrayDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(LET);
			setState(67);
			identifier();
			setState(68);
			match(T__0);
			setState(69);
			match(T__2);
			setState(70);
			dataType();
			setState(71);
			match(T__1);
			setState(72);
			match(INTEGER);
			setState(73);
			match(T__3);
			setState(74);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantDeclarationContext extends ParserRuleContext {
		public TerminalNode DEFINE() { return getToken(MiniSoftParser.DEFINE, 0); }
		public TerminalNode CONST() { return getToken(MiniSoftParser.CONST, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConstantDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitConstantDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantDeclarationContext constantDeclaration() throws RecognitionException {
		ConstantDeclarationContext _localctx = new ConstantDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_constantDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(DEFINE);
			setState(77);
			match(CONST);
			setState(78);
			identifier();
			setState(79);
			match(T__0);
			setState(80);
			dataType();
			setState(81);
			match(T__4);
			setState(82);
			expression(0);
			setState(83);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			identifier();
			setState(86);
			match(T__5);
			setState(87);
			expression(0);
			setState(88);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(MiniSoftParser.IF, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(MiniSoftParser.THEN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(MiniSoftParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(IF);
			setState(91);
			match(T__6);
			setState(92);
			condition(0);
			setState(93);
			match(T__7);
			setState(94);
			match(THEN);
			setState(95);
			match(T__8);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LET) | (1L << DEFINE) | (1L << IF) | (1L << DO) | (1L << FOR) | (1L << INPUT) | (1L << OUTPUT) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(96);
				statement();
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(102);
			match(T__9);
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(103);
				match(ELSE);
				setState(104);
				match(T__8);
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LET) | (1L << DEFINE) | (1L << IF) | (1L << DO) | (1L << FOR) | (1L << INPUT) | (1L << OUTPUT) | (1L << IDENTIFIER))) != 0)) {
					{
					{
					setState(105);
					statement();
					}
					}
					setState(110);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(111);
				match(T__9);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopStatementContext extends ParserRuleContext {
		public DoWhileLoopContext doWhileLoop() {
			return getRuleContext(DoWhileLoopContext.class,0);
		}
		public ForLoopContext forLoop() {
			return getRuleContext(ForLoopContext.class,0);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_loopStatement);
		try {
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DO:
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				doWhileLoop();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				forLoop();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoWhileLoopContext extends ParserRuleContext {
		public TerminalNode DO() { return getToken(MiniSoftParser.DO, 0); }
		public TerminalNode WHILE() { return getToken(MiniSoftParser.WHILE, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public DoWhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitDoWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileLoopContext doWhileLoop() throws RecognitionException {
		DoWhileLoopContext _localctx = new DoWhileLoopContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_doWhileLoop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(DO);
			setState(119);
			match(T__8);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LET) | (1L << DEFINE) | (1L << IF) | (1L << DO) | (1L << FOR) | (1L << INPUT) | (1L << OUTPUT) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(120);
				statement();
				}
				}
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126);
			match(T__9);
			setState(127);
			match(WHILE);
			setState(128);
			match(T__6);
			setState(129);
			condition(0);
			setState(130);
			match(T__7);
			setState(131);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForLoopContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(MiniSoftParser.FOR, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FROM() { return getToken(MiniSoftParser.FROM, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode TO() { return getToken(MiniSoftParser.TO, 0); }
		public TerminalNode STEP() { return getToken(MiniSoftParser.STEP, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ForLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitForLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForLoopContext forLoop() throws RecognitionException {
		ForLoopContext _localctx = new ForLoopContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_forLoop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(FOR);
			setState(134);
			identifier();
			setState(135);
			match(FROM);
			setState(136);
			expression(0);
			setState(137);
			match(TO);
			setState(138);
			expression(0);
			setState(139);
			match(STEP);
			setState(140);
			expression(0);
			setState(141);
			match(T__8);
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LET) | (1L << DEFINE) | (1L << IF) | (1L << DO) | (1L << FOR) | (1L << INPUT) | (1L << OUTPUT) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(142);
				statement();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IoStatementContext extends ParserRuleContext {
		public InputStatementContext inputStatement() {
			return getRuleContext(InputStatementContext.class,0);
		}
		public OutputStatementContext outputStatement() {
			return getRuleContext(OutputStatementContext.class,0);
		}
		public IoStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ioStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitIoStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IoStatementContext ioStatement() throws RecognitionException {
		IoStatementContext _localctx = new IoStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ioStatement);
		try {
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INPUT:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				inputStatement();
				}
				break;
			case OUTPUT:
				enterOuterAlt(_localctx, 2);
				{
				setState(151);
				outputStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputStatementContext extends ParserRuleContext {
		public TerminalNode INPUT() { return getToken(MiniSoftParser.INPUT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public InputStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inputStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitInputStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputStatementContext inputStatement() throws RecognitionException {
		InputStatementContext _localctx = new InputStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_inputStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			match(INPUT);
			setState(155);
			match(T__6);
			setState(156);
			identifier();
			setState(157);
			match(T__7);
			setState(158);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputStatementContext extends ParserRuleContext {
		public TerminalNode OUTPUT() { return getToken(MiniSoftParser.OUTPUT, 0); }
		public TerminalNode STRING() { return getToken(MiniSoftParser.STRING, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public OutputStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outputStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitOutputStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputStatementContext outputStatement() throws RecognitionException {
		OutputStatementContext _localctx = new OutputStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_outputStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(OUTPUT);
			setState(161);
			match(T__6);
			setState(171);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				{
				setState(162);
				match(STRING);
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(163);
					match(T__10);
					setState(164);
					identifier();
					}
					}
					setState(169);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case IDENTIFIER:
				{
				setState(170);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(173);
			match(T__7);
			setState(174);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	 
		public ConditionContext() { }
		public void copyFrom(ConditionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OrConditionContext extends ConditionContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode OR() { return getToken(MiniSoftParser.OR, 0); }
		public OrConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitOrCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndConditionContext extends ConditionContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode AND() { return getToken(MiniSoftParser.AND, 0); }
		public AndConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitAndCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesizedConditionContext extends ConditionContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public ParenthesizedConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitParenthesizedCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotConditionContext extends ConditionContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public NotConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitNotCondition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ComparisonConditionContext extends ConditionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public ComparisonConditionContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitComparisonCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		return condition(0);
	}

	private ConditionContext condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
		ConditionContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_condition, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				_localctx = new ComparisonConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(177);
				expression(0);
				setState(178);
				comparisonOperator();
				setState(179);
				expression(0);
				}
				break;
			case 2:
				{
				_localctx = new NotConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(181);
				match(T__11);
				setState(182);
				condition(2);
				}
				break;
			case 3:
				{
				_localctx = new ParenthesizedConditionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(183);
				match(T__6);
				setState(184);
				condition(0);
				setState(185);
				match(T__7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(197);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(195);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new AndConditionContext(new ConditionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(189);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(190);
						match(AND);
						setState(191);
						condition(5);
						}
						break;
					case 2:
						{
						_localctx = new OrConditionContext(new ConditionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_condition);
						setState(192);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(193);
						match(OR);
						setState(194);
						condition(4);
						}
						break;
					}
					} 
				}
				setState(199);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArrayAccessExprContext extends ExpressionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayAccessExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitArrayAccessExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegationExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NegationExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitNegationExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddSubExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AddSubExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitAddSubExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrimaryExprContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public PrimaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulDivExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MulDivExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitMulDivExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				_localctx = new PrimaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(201);
				primary();
				}
				break;
			case 2:
				{
				_localctx = new ArrayAccessExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(202);
				identifier();
				setState(203);
				match(T__2);
				setState(204);
				expression(0);
				setState(205);
				match(T__3);
				}
				break;
			case 3:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(207);
				match(T__6);
				setState(208);
				expression(0);
				setState(209);
				match(T__7);
				}
				break;
			case 4:
				{
				_localctx = new NegationExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(211);
				match(T__12);
				setState(212);
				expression(3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(223);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(221);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(215);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(216);
						_la = _input.LA(1);
						if ( !(_la==T__13 || _la==T__14) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(217);
						expression(3);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(218);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(219);
						_la = _input.LA(1);
						if ( !(_la==T__12 || _la==T__15) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(220);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(225);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode INTEGER() { return getToken(MiniSoftParser.INTEGER, 0); }
		public TerminalNode FLOAT() { return getToken(MiniSoftParser.FLOAT, 0); }
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_primary);
		try {
			setState(229);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(226);
				identifier();
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 2);
				{
				setState(227);
				match(INTEGER);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 3);
				{
				setState(228);
				match(FLOAT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MiniSoftParser.IDENTIFIER, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataTypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(MiniSoftParser.INT, 0); }
		public TerminalNode FLOAT_TYPE() { return getToken(MiniSoftParser.FLOAT_TYPE, 0); }
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_dataType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==FLOAT_TYPE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonOperatorContext extends ParserRuleContext {
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniSoftVisitor ) return ((MiniSoftVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 14:
			return condition_sempred((ConditionContext)_localctx, predIndex);
		case 15:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001/\u00ee\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0001\u0000\u0005\u0000*\b\u0000\n\u0000\f\u0000"+
		"-\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u00016\b\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002;\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007b\b\u0007"+
		"\n\u0007\f\u0007e\t\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0005\u0007k\b\u0007\n\u0007\f\u0007n\t\u0007\u0001\u0007\u0003\u0007"+
		"q\b\u0007\u0001\b\u0001\b\u0003\bu\b\b\u0001\t\u0001\t\u0001\t\u0005\t"+
		"z\b\t\n\t\f\t}\t\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0005\n\u0090\b\n\n\n\f\n\u0093\t\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0003\u000b\u0099\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00a6"+
		"\b\r\n\r\f\r\u00a9\t\r\u0001\r\u0003\r\u00ac\b\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00bc\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0005\u000e\u00c4\b\u000e\n\u000e\f\u000e\u00c7\t\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0003\u000f\u00d6\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00de\b\u000f\n\u000f\f\u000f"+
		"\u00e1\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00e6\b"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0000\u0002\u001c\u001e\u0014\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000"+
		"\u0004\u0001\u0000\u000e\u000f\u0002\u0000\r\r\u0010\u0010\u0001\u0000"+
		"%&\u0001\u0000\u0011\u0016\u00f4\u0000+\u0001\u0000\u0000\u0000\u0002"+
		"5\u0001\u0000\u0000\u0000\u0004:\u0001\u0000\u0000\u0000\u0006<\u0001"+
		"\u0000\u0000\u0000\bB\u0001\u0000\u0000\u0000\nL\u0001\u0000\u0000\u0000"+
		"\fU\u0001\u0000\u0000\u0000\u000eZ\u0001\u0000\u0000\u0000\u0010t\u0001"+
		"\u0000\u0000\u0000\u0012v\u0001\u0000\u0000\u0000\u0014\u0085\u0001\u0000"+
		"\u0000\u0000\u0016\u0098\u0001\u0000\u0000\u0000\u0018\u009a\u0001\u0000"+
		"\u0000\u0000\u001a\u00a0\u0001\u0000\u0000\u0000\u001c\u00bb\u0001\u0000"+
		"\u0000\u0000\u001e\u00d5\u0001\u0000\u0000\u0000 \u00e5\u0001\u0000\u0000"+
		"\u0000\"\u00e7\u0001\u0000\u0000\u0000$\u00e9\u0001\u0000\u0000\u0000"+
		"&\u00eb\u0001\u0000\u0000\u0000(*\u0003\u0002\u0001\u0000)(\u0001\u0000"+
		"\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001"+
		"\u0000\u0000\u0000,.\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000"+
		"./\u0005\u0000\u0000\u0001/\u0001\u0001\u0000\u0000\u000006\u0003\u0004"+
		"\u0002\u000016\u0003\f\u0006\u000026\u0003\u000e\u0007\u000036\u0003\u0010"+
		"\b\u000046\u0003\u0016\u000b\u000050\u0001\u0000\u0000\u000051\u0001\u0000"+
		"\u0000\u000052\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000054\u0001"+
		"\u0000\u0000\u00006\u0003\u0001\u0000\u0000\u00007;\u0003\u0006\u0003"+
		"\u00008;\u0003\b\u0004\u00009;\u0003\n\u0005\u0000:7\u0001\u0000\u0000"+
		"\u0000:8\u0001\u0000\u0000\u0000:9\u0001\u0000\u0000\u0000;\u0005\u0001"+
		"\u0000\u0000\u0000<=\u0005\u0017\u0000\u0000=>\u0003\"\u0011\u0000>?\u0005"+
		"\u0001\u0000\u0000?@\u0003$\u0012\u0000@A\u0005\u0002\u0000\u0000A\u0007"+
		"\u0001\u0000\u0000\u0000BC\u0005\u0017\u0000\u0000CD\u0003\"\u0011\u0000"+
		"DE\u0005\u0001\u0000\u0000EF\u0005\u0003\u0000\u0000FG\u0003$\u0012\u0000"+
		"GH\u0005\u0002\u0000\u0000HI\u0005)\u0000\u0000IJ\u0005\u0004\u0000\u0000"+
		"JK\u0005\u0002\u0000\u0000K\t\u0001\u0000\u0000\u0000LM\u0005\u0018\u0000"+
		"\u0000MN\u0005\u0019\u0000\u0000NO\u0003\"\u0011\u0000OP\u0005\u0001\u0000"+
		"\u0000PQ\u0003$\u0012\u0000QR\u0005\u0005\u0000\u0000RS\u0003\u001e\u000f"+
		"\u0000ST\u0005\u0002\u0000\u0000T\u000b\u0001\u0000\u0000\u0000UV\u0003"+
		"\"\u0011\u0000VW\u0005\u0006\u0000\u0000WX\u0003\u001e\u000f\u0000XY\u0005"+
		"\u0002\u0000\u0000Y\r\u0001\u0000\u0000\u0000Z[\u0005\u001a\u0000\u0000"+
		"[\\\u0005\u0007\u0000\u0000\\]\u0003\u001c\u000e\u0000]^\u0005\b\u0000"+
		"\u0000^_\u0005\u001b\u0000\u0000_c\u0005\t\u0000\u0000`b\u0003\u0002\u0001"+
		"\u0000a`\u0001\u0000\u0000\u0000be\u0001\u0000\u0000\u0000ca\u0001\u0000"+
		"\u0000\u0000cd\u0001\u0000\u0000\u0000df\u0001\u0000\u0000\u0000ec\u0001"+
		"\u0000\u0000\u0000fp\u0005\n\u0000\u0000gh\u0005\u001c\u0000\u0000hl\u0005"+
		"\t\u0000\u0000ik\u0003\u0002\u0001\u0000ji\u0001\u0000\u0000\u0000kn\u0001"+
		"\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000"+
		"mo\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000oq\u0005\n\u0000\u0000"+
		"pg\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000q\u000f\u0001\u0000"+
		"\u0000\u0000ru\u0003\u0012\t\u0000su\u0003\u0014\n\u0000tr\u0001\u0000"+
		"\u0000\u0000ts\u0001\u0000\u0000\u0000u\u0011\u0001\u0000\u0000\u0000"+
		"vw\u0005\u001d\u0000\u0000w{\u0005\t\u0000\u0000xz\u0003\u0002\u0001\u0000"+
		"yx\u0001\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000"+
		"\u0000{|\u0001\u0000\u0000\u0000|~\u0001\u0000\u0000\u0000}{\u0001\u0000"+
		"\u0000\u0000~\u007f\u0005\n\u0000\u0000\u007f\u0080\u0005\u001e\u0000"+
		"\u0000\u0080\u0081\u0005\u0007\u0000\u0000\u0081\u0082\u0003\u001c\u000e"+
		"\u0000\u0082\u0083\u0005\b\u0000\u0000\u0083\u0084\u0005\u0002\u0000\u0000"+
		"\u0084\u0013\u0001\u0000\u0000\u0000\u0085\u0086\u0005\u001f\u0000\u0000"+
		"\u0086\u0087\u0003\"\u0011\u0000\u0087\u0088\u0005 \u0000\u0000\u0088"+
		"\u0089\u0003\u001e\u000f\u0000\u0089\u008a\u0005!\u0000\u0000\u008a\u008b"+
		"\u0003\u001e\u000f\u0000\u008b\u008c\u0005\"\u0000\u0000\u008c\u008d\u0003"+
		"\u001e\u000f\u0000\u008d\u0091\u0005\t\u0000\u0000\u008e\u0090\u0003\u0002"+
		"\u0001\u0000\u008f\u008e\u0001\u0000\u0000\u0000\u0090\u0093\u0001\u0000"+
		"\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000"+
		"\u0000\u0000\u0092\u0094\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000"+
		"\u0000\u0000\u0094\u0095\u0005\n\u0000\u0000\u0095\u0015\u0001\u0000\u0000"+
		"\u0000\u0096\u0099\u0003\u0018\f\u0000\u0097\u0099\u0003\u001a\r\u0000"+
		"\u0098\u0096\u0001\u0000\u0000\u0000\u0098\u0097\u0001\u0000\u0000\u0000"+
		"\u0099\u0017\u0001\u0000\u0000\u0000\u009a\u009b\u0005#\u0000\u0000\u009b"+
		"\u009c\u0005\u0007\u0000\u0000\u009c\u009d\u0003\"\u0011\u0000\u009d\u009e"+
		"\u0005\b\u0000\u0000\u009e\u009f\u0005\u0002\u0000\u0000\u009f\u0019\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0005$\u0000\u0000\u00a1\u00ab\u0005\u0007"+
		"\u0000\u0000\u00a2\u00a7\u0005,\u0000\u0000\u00a3\u00a4\u0005\u000b\u0000"+
		"\u0000\u00a4\u00a6\u0003\"\u0011\u0000\u00a5\u00a3\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00ac\u0001\u0000\u0000\u0000"+
		"\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ac\u0003\"\u0011\u0000\u00ab"+
		"\u00a2\u0001\u0000\u0000\u0000\u00ab\u00aa\u0001\u0000\u0000\u0000\u00ac"+
		"\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005\b\u0000\u0000\u00ae\u00af"+
		"\u0005\u0002\u0000\u0000\u00af\u001b\u0001\u0000\u0000\u0000\u00b0\u00b1"+
		"\u0006\u000e\uffff\uffff\u0000\u00b1\u00b2\u0003\u001e\u000f\u0000\u00b2"+
		"\u00b3\u0003&\u0013\u0000\u00b3\u00b4\u0003\u001e\u000f\u0000\u00b4\u00bc"+
		"\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005\f\u0000\u0000\u00b6\u00bc\u0003"+
		"\u001c\u000e\u0002\u00b7\u00b8\u0005\u0007\u0000\u0000\u00b8\u00b9\u0003"+
		"\u001c\u000e\u0000\u00b9\u00ba\u0005\b\u0000\u0000\u00ba\u00bc\u0001\u0000"+
		"\u0000\u0000\u00bb\u00b0\u0001\u0000\u0000\u0000\u00bb\u00b5\u0001\u0000"+
		"\u0000\u0000\u00bb\u00b7\u0001\u0000\u0000\u0000\u00bc\u00c5\u0001\u0000"+
		"\u0000\u0000\u00bd\u00be\n\u0004\u0000\u0000\u00be\u00bf\u0005\'\u0000"+
		"\u0000\u00bf\u00c4\u0003\u001c\u000e\u0005\u00c0\u00c1\n\u0003\u0000\u0000"+
		"\u00c1\u00c2\u0005(\u0000\u0000\u00c2\u00c4\u0003\u001c\u000e\u0004\u00c3"+
		"\u00bd\u0001\u0000\u0000\u0000\u00c3\u00c0\u0001\u0000\u0000\u0000\u00c4"+
		"\u00c7\u0001\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5"+
		"\u00c6\u0001\u0000\u0000\u0000\u00c6\u001d\u0001\u0000\u0000\u0000\u00c7"+
		"\u00c5\u0001\u0000\u0000\u0000\u00c8\u00c9\u0006\u000f\uffff\uffff\u0000"+
		"\u00c9\u00d6\u0003 \u0010\u0000\u00ca\u00cb\u0003\"\u0011\u0000\u00cb"+
		"\u00cc\u0005\u0003\u0000\u0000\u00cc\u00cd\u0003\u001e\u000f\u0000\u00cd"+
		"\u00ce\u0005\u0004\u0000\u0000\u00ce\u00d6\u0001\u0000\u0000\u0000\u00cf"+
		"\u00d0\u0005\u0007\u0000\u0000\u00d0\u00d1\u0003\u001e\u000f\u0000\u00d1"+
		"\u00d2\u0005\b\u0000\u0000\u00d2\u00d6\u0001\u0000\u0000\u0000\u00d3\u00d4"+
		"\u0005\r\u0000\u0000\u00d4\u00d6\u0003\u001e\u000f\u0003\u00d5\u00c8\u0001"+
		"\u0000\u0000\u0000\u00d5\u00ca\u0001\u0000\u0000\u0000\u00d5\u00cf\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00df\u0001"+
		"\u0000\u0000\u0000\u00d7\u00d8\n\u0002\u0000\u0000\u00d8\u00d9\u0007\u0000"+
		"\u0000\u0000\u00d9\u00de\u0003\u001e\u000f\u0003\u00da\u00db\n\u0001\u0000"+
		"\u0000\u00db\u00dc\u0007\u0001\u0000\u0000\u00dc\u00de\u0003\u001e\u000f"+
		"\u0002\u00dd\u00d7\u0001\u0000\u0000\u0000\u00dd\u00da\u0001\u0000\u0000"+
		"\u0000\u00de\u00e1\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000\u0000"+
		"\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\u001f\u0001\u0000\u0000"+
		"\u0000\u00e1\u00df\u0001\u0000\u0000\u0000\u00e2\u00e6\u0003\"\u0011\u0000"+
		"\u00e3\u00e6\u0005)\u0000\u0000\u00e4\u00e6\u0005*\u0000\u0000\u00e5\u00e2"+
		"\u0001\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e4"+
		"\u0001\u0000\u0000\u0000\u00e6!\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005"+
		"+\u0000\u0000\u00e8#\u0001\u0000\u0000\u0000\u00e9\u00ea\u0007\u0002\u0000"+
		"\u0000\u00ea%\u0001\u0000\u0000\u0000\u00eb\u00ec\u0007\u0003\u0000\u0000"+
		"\u00ec\'\u0001\u0000\u0000\u0000\u0013+5:clpt{\u0091\u0098\u00a7\u00ab"+
		"\u00bb\u00c3\u00c5\u00d5\u00dd\u00df\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}