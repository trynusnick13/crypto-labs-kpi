package main

import (
	"flag"
	"fmt"
	"lab2/pkg/crypto"
	"lab2/pkg/files"
)



func main() {
	keyCliArg := flag.String("aes_key", "Нікіта ТринусДА-91", "key for AES actions")
	inputFileNameCliArg := flag.String("input_file_name", "вариант 17.txt", "file to extract the data from")
	outputFileNameCliArg := flag.String("output_file_name", "encoded_new_1", "destination file for encrypted data")
	modeCliArg := flag.String("mode", "encrypt", "choose encrypt/decrypt")
	flag.Parse()

	possibleKeys := [3]int{16, 24, 32}
	isValid := false
	for key := range possibleKeys {
        if key == len(*keyCliArg) {
			isValid = true
        }
    }
	if !isValid {
		panic("Not valid key size was provided!")
	}
	fmt.Printf("Len of the key %d\n", len(*keyCliArg))
	if *modeCliArg == "encrypt" {
		plainText := string(files.ReadFromFile(*inputFileNameCliArg))
		encryptedText := crypto.Encrypt([]byte(*keyCliArg), plainText)
		files.WriteToFile(*outputFileNameCliArg, encryptedText)
		fmt.Printf("Wrote encrypted data to file %s\n", *outputFileNameCliArg)
	} else {
		fmt.Println(crypto.Decrypt([]byte(*keyCliArg), *outputFileNameCliArg))
	}

}
