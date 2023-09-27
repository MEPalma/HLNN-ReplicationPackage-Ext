#!/bin/bash

FILES="src/main/python/saved_model_losses/*"
for f in $FILES
do
    fname="$(basename -- $f)"
    fnamenoext="${fname%.*}"

    echo "$fnamenoext"
        if [[ $fname == javascript* ]];
            then
            ./gradlew JavaScriptEvaluator -Pargs="perFileAccModel $fname false"
            echo "$fname"

        elif [[ $fname == java* ]];
            then
            ./gradlew JavaEvaluator -Pargs="perFileAccModel $fname"
            echo "$fname"

        elif [[ $fname == kotlin* ]];
            then
            ./gradlew KotlinEvaluator -Pargs="perFileAccModel $fname"
            echo "$fname"

        elif [[ $fname == python* ]];
            then
            ./gradlew Python3Evaluator -Pargs="perFileAccModel $fname"
            echo "$fname"

        elif [[ $fname == cpp* ]];
            then
            ./gradlew CPPEvaluator -Pargs="perFileAccModel $fname false"
            echo "$fname"

        elif [[ $fname == csharp* ]];
            then
            ./gradlew CSharpEvaluator -Pargs="perFileAccModel $fname false"
            echo "$fname"
        else
            echo "Invalid $fname"
        fi

done

./gradlew CSharpEvaluator -Pargs="perFileAccPygments"
./gradlew JavaScriptEvaluator -Pargs="perFileAccPygments"
./gradlew JavaEvaluator -Pargs="perFileAccPygments"
./gradlew KotlinEvaluator -Pargs="perFileAccPygments"
./gradlew Python3Evaluator -Pargs="perFileAccPygments"
./gradlew CPPEvaluator -Pargs="perFileAccPygments"

./gradlew CSharpEvaluator -Pargs="perFileAccBrute"
./gradlew JavaScriptEvaluator -Pargs="perFileAccBrute"
./gradlew JavaEvaluator -Pargs="perFileAccBrute"
./gradlew KotlinEvaluator -Pargs="perFileAccBrute"
./gradlew Python3Evaluator -Pargs="perFileAccBrute"
./gradlew CPPEvaluator -Pargs="perFileAccBrute"

