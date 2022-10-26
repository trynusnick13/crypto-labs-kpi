package files

import (
	"io/ioutil"
	"lab2/pkg/utils"
)


func WriteToFile(fileName string, raw []byte){
	err := ioutil.WriteFile(fileName, raw, 0644)
	utils.CheckError(err)
}