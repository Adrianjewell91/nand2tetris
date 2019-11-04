echo "token tests"
TextComparer.sh Test/ArrayTestT.xml ArrayTest/MainT.xml;
TextComparer.sh Test/SquareMainT.xml Square/MainT.xml;
TextComparer.sh Test/SquareGameT.xml Square/SquareGameT.xml;
TextComparer.sh Test/SquareT.xml Square/SquareT.xml;
echo "parse tests"
echo "array"
TextComparer.sh Test/ArrayTest.xml ArrayTest/Main.xml
echo "square object"
TextComparer.sh Test/Square.xml Square/Square.xml
echo "square main"
TextComparer.sh Test/SquareMain.xml Square/Main.xml
echo "square game"
TextComparer.sh Test/SquareGame.xml Square/SquareGame.xml