// test.js
import antlr4 from 'antlr4';
import MyGrammarLexer from './QueryLexer.js';
import MyGrammarParser from './QueryParser.js';
import MyGrammarListener from './QueryListener.js';

const input = "field = 123 AND items in (1,2,3)"
const chars = new antlr4.InputStream(input);
const lexer = new MyGrammarLexer(chars);
const tokens = new antlr4.CommonTokenStream(lexer);
const parser = new MyGrammarParser(tokens);
parser.buildParseTrees = true
const tree = parser.MyQuery();

const myFunc = function(input, input2){
	return input * input2;
};

class Visitor {
  visitChildren(ctx) {
    if (!ctx) {
      return;
    }

    if (ctx.children) {
      return ctx.children.map(child => {
	const bla = "test";
        if (child.children && child.children.length != 0) {
          return child.accept(this);
        } else {
          return child.getText();
        }
      });
    }
  }
}
tree.accept(new Visitor());

class MyGrammarListener extends ParseTreeListener {
    constructor() {
        super();
    }
   
    enterKey(ctx) {}
    exitKey(ctx) {}
    enterValue(ctx) {}
    exitValue(ctx) {}
}
