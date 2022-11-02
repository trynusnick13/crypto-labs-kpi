package files

import (
	"io/ioutil"
	"lab2/pkg/utils"
)

func ReadFromFile(fileName string) []byte {
	raw_content, error := ioutil.ReadFile(fileName)
	utils.CheckError(error)

	return raw_content
}
