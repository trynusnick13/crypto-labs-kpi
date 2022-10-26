package crypto

import (
	"crypto/aes"
	"crypto/cipher"
	"lab2/pkg/utils"
	"strings"
)

// AES encryption mechanism
func Encrypt(key []byte, plainText string) []byte {
	cipher, err := aes.NewCipher(key)
	utils.CheckError(err)
	
	encryptedText := make([]byte, 0, len(plainText))
	for offset := 0; offset < len(plainText); offset += BlockSize {
		rightLimit := offset + BlockSize
		if rightLimit > len(plainText) {
			rightLimit = len(plainText)
		}
		
		ch := make(chan []byte)
		plainTextBatch := plainText[offset:rightLimit]
		if len(plainTextBatch) < BlockSize {
			plainTextBatch = plainTextBatch + strings.Repeat("0", BlockSize - len(plainTextBatch))
		}
		go encryptBatch(cipher, ch, plainTextBatch)
		encryptedBatch := <- ch
		encryptedText = append(encryptedText, encryptedBatch...)
	}

	return encryptedText
}

func encryptBatch(cipherAES cipher.Block, ch chan []byte, plainTextBatch string){
	encryptedBatch := make([]byte, BlockSize)
	cipherAES.Encrypt(encryptedBatch, []byte(plainTextBatch))

	ch <- encryptedBatch
}
