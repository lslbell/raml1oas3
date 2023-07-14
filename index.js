#!/usr/bin/env node

const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

var sourceFilePath;

rl.question('Enter the raml file source path: ', (filePath) => {
    console.log(`${filePath}`);

    sourceFilePath = filePath.trim();

    rl.close();
});
