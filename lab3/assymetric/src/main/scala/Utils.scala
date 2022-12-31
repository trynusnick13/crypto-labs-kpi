import scala.io.Source
import java.io.{BufferedInputStream, BufferedOutputStream, File, FileInputStream, FileOutputStream, FileWriter}

object Utils {
  def open(path: String) = new File(path)

  def readBinary(inputFileName: String): Array[Byte] = {
    val bis = new BufferedInputStream(new FileInputStream(inputFileName))
    bis.readAllBytes()

  }

  def writeBinary(destFileName: String, data: Array[Byte]): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(destFileName))
    data.foreach(b => bos.write(b))

    bos.close

  }

  def writeTextToFile(fileName: String, data: String): Unit = {
    val fileWriter = new FileWriter(new File(fileName))
    fileWriter.write(data)
    fileWriter.close()
  }

  implicit class QuickReadTextFile(file: File) {
    def read() = Source.fromFile(file).getLines()
  }
}
