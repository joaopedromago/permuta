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

// testando tempo levado para executar as funções com as seguintes quantidades:
const tests = [4, 8, 10, 14, 16, 20];

const run = () => {
    tests.forEach(test => {
        const startDate = new Date();
        console.log(`Running for ${test}`);
        queenPerm(0, [], test);
        const endDate = new Date();
        console.log(`Total time: ${endDate.getTime() - startDate.getTime()} miliseconds`)
    });
}

run();