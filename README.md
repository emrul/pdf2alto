#概要

PDFBOXを利用して、透明テキスト付きPDFから文字座標情報を抽出し、ALTOフォーマット（https://www.loc.gov/standards/alto/）で出力するツール。

#ビルド

mvn clean install

#実行

  java -jar PDF2ALTO-0.1-jar-with-dependencies <pdfファイル名> <xml出力ディレクトリ名> <dx> <dy> <sx> <sy> <画像ファイル出力ディレクトリ名>

pdfファイル名：インプットとなるPDFファイル
xml出力ディレクトリ名：ALTO XMLを出力するディレクトリ。1ページにつき1XMLファイルが出力され、ファイル名は「alto_3桁の連番.xml」となる。
dx,dy,sx,sy：ALTO XML上の座標情報を微修正するパラメータ。dx,dyは変位、sx,syはスケール。
画像ファイル出力ディレクトリ：動作確認用に、検出した文字のバウンダリを画像ファイルに出力するフォルダ。オプショナル。