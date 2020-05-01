const prompt = require('prompt');

const queenPerm = (n, auxArr, size) => {
    if (auxArr.length === size) {
        console.log(auxArr)
    } else {
        for (let j = 0; j < size; j++) {
            auxArr[n] = j;
            if (valid([...auxArr])) {
                queenPerm(n + 1, [...auxArr], size);
            }
        }
    }
}

const valid = (arr) => {
    for (let i = 0; i < arr.length; i++) {
        for (let j = arr.length - 1; j >= 0; j--) {
            if ((arr[i] === arr[j] || Math.abs(arr[i] - arr[j]) === Math.abs(i - j)) && i != j) {
                return false;
            }
        }
    }
    return true;
}

prompt.start();

console.log("Digite o número de rainhas: ");
prompt.get(['number'], function (err, result) {
    if(err) {
        throw new Error("Invalid Input");
    }
    const startDate = new Date();
    console.log(`Running for ${result.number}`);
    queenPerm(0, [], result.number);
    const endDate = new Date();
    console.log(`Total time: ${endDate.getTime() - startDate.getTime()} miliseconds`)
});
