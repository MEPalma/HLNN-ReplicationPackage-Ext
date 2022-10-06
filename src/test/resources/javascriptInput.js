// test.js
import antlr4 from 'antlr4';
import MyGrammarLexer from './QueryLexer.js';
import MyGrammarParser from './QueryParser.js';
import MyGrammarListener from './QueryListener.js';

const input = "field = 123 AND items in (1,2,3)";
const chars = new antlr4.InputStream(input);
const lexer = new MyGrammarLexer(chars);
const tokens = new antlr4.CommonTokenStream(lexer);
const parser = new MyGrammarParser(tokens);
parser.buildParseTrees = true
const tree = parser.MyQuery();

const x = BigInt(Number.MAX_SAFE_INTEGER); // 9007199254740991n

const myFunc = function (input, input2) {
    return input * input2;
};
const yield = 4;
const async = 5;
var let = 10;

function* foo(index) {
    while (index < 2) {
        yield index;
        index++;
    }
}

async function asyncCall() {
    console.log('calling');
    const result = await resolveAfter2Seconds();
    console.log(result);
    // expected output: "resolved"
}


const hero = {
    name: 'Batman'
};
// Dot property accessor
hero.name; // => 'Batman'


var myArray = [0, 1, 2];

myArray[0].a.b().c.d[0].e()[1];

class Visitor extends Bla {
    exitStuff(ctx) {
    }

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

    enterKey(ctx) {
        doStuff();
        doStuff()
        hero["name"];
        getList().add(new Element());
        getList().add(new Element())
        var x = 13;
        let i = 33;
        let nn = null;
    }

    exitKey(ctx) {
    }

    enterValue(ctx) {
    }

    exitValue(ctx) {
    }
}

function myStrictFunction() {
    // Function-level strict mode syntax
    'use strict';

    function nested() {
        return 'And so am I!';
    }

    return 'test';
}
