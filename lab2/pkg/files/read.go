package files

import (
	"io/ioutil"
	"lab2/pkg/utils"
)

func ReadFromFile(fileName string) string {
	raw_content, error := ioutil.ReadFile(fileName)
	utils.CheckError(error)

	return string(raw_content)
}
