const perm = (n, auxArr, arr) => {
    if (auxArr.length === arr.length) {
        console.log(auxArr)
    } else {
        arr.forEach(element => {
            auxArr[n] = element;
            if (valid([...auxArr])) {
                perm(n + 1, [...auxArr], arr);
            }
        });
    }
}

const valid = (arr) => {
    for (let index = 0; index < arr.length - 1; index++) {
        for (let indexReverse = arr.length; indexReverse > 0; indexReverse--) {
            if (arr[index] === arr[indexReverse] && index != indexReverse) {
                return false;
            }
        }
    }
    return true;
}

perm(0, [], [1, 2, 3]);