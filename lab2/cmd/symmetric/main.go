package main

import (
	"flag"
	"fmt"
	"lab2/pkg/crypto"
	"lab2/pkg/files"
)

func main() {
	keyCliArg := flag.String("aes_key", "Нікіта ТринусДА-91", "key for AES actions")
	fileNameCliArg := flag.String("file_name", "вариант 17.txt", "file to extract the data from")
	flag.Parse()
	key := *keyCliArg
	fileName := *fileNameCliArg

	plainText := files.ReadFromFile(fileName)
	encryptedText := crypto.Encrypt([]byte(key), plainText)
	files.WriteToFile("encoded_new", encryptedText)
	fmt.Println(crypto.Decrypt([]byte(key), encryptedText))

}
