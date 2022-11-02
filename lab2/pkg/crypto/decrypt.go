package crypto

import (
	"crypto/aes"
	"crypto/cipher"
	"lab2/pkg/files"
	"lab2/pkg/utils"
)

// AES decryption mechanism
func Decrypt(key []byte, inputFileName string) string {
	cipher, err := aes.NewCipher(key)
	utils.CheckError(err)

	encryptedText := files.ReadFromFile(inputFileName)
	plainText := make([]byte, 0, len(encryptedText))
	for offset := 0; offset < len(encryptedText); offset += BlockSize {
		rightLimit := offset + BlockSize
		ch := make(chan []byte)
		if rightLimit > len(encryptedText) {
			rightLimit = len(encryptedText)
		}
		encryptedBatch := encryptedText[offset:rightLimit]
		go decryptBatch(cipher, ch, encryptedBatch)
		plainTextBatch := <- ch
		plainText = append(plainText, plainTextBatch...)
	}

	return string(plainText)
}

func decryptBatch(cipherAES cipher.Block, ch chan []byte, encryptedBatch []byte){
	plainTextBatch := make([]byte, BlockSize)
	cipherAES.Decrypt(plainTextBatch, encryptedBatch)

	ch <- plainTextBatch
}
