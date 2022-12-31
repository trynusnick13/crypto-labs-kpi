import Utils._

object Main {
  def main(args: Array[String]) = {
    val argsCli = Map.newBuilder[String, String]
    args.sliding(2, 2).toList.collect {
      case Array("--input", input: String) => argsCli.+=("input" -> input)
      case Array("--output", output: String) => argsCli.+=("output" -> output)
      case Array("--pub-name", pub: String) => argsCli.+=("pub-name" -> pub)
      case Array("--pvt-name", pvt: String) => argsCli.+=("pvt-name" -> pvt)
      case Array("--mode", mode: String) => argsCli.+=("mode" -> mode)
    }

    println(argsCli.result())

    val jobConfig = argsCli.result()
    jobConfig("mode") match {
      case "encrypt" => {
        val bytes = Utils.readBinary(jobConfig("input"))
        val loadedPubKey = Crypto.loadPublicKeyFromFile(jobConfig("pub-name"))
        val encryptedData = Crypto.encrypt(loadedPubKey, bytes)

        writeBinary(jobConfig("output"), encryptedData)
      }
      case "decrypt" => {
        val loadedPvtKey = Crypto.loadPrivateKeyFromFile(jobConfig("pvt-name"))
        val encryptedDataRead = Utils.readBinary(jobConfig("input"))
        val decryptedData = Crypto.decrypt(loadedPvtKey, encryptedDataRead)

        writeTextToFile(jobConfig("output"), (decryptedData.map(_.toChar)).mkString)
      }
      case "generateKeys" => {
        val (pubKey, pvtKey) = Crypto.generateKeyPair()
        writeBinary(jobConfig("pvt-name"), pvtKey.getEncoded)
        writeBinary(jobConfig("pub-name"), pubKey.getEncoded)
      }
      case _ =>
    }
//    val bytes = Utils.readBinary("hello.txt")
//    val (pubKey, pvtKey) = Crypto.generateKeyPair()
//    writeBinary("key.private", pvtKey.getEncoded)
//    writeBinary("key.public", pubKey.getEncoded)
//
//    val loadedPvtKey = Crypto.loadPrivateKeyFromFile("key.private")
//    val loadedPubKey = Crypto.loadPublicKeyFromFile("key.public")
//    val encryptedData = Crypto.encrypt(loadedPubKey, bytes)
//    writeBinary("encrypted_data", encryptedData)
//    val encryptedDataRead = Utils.readBinary("encrypted_data")
//
//    val decryptedData = Crypto.decrypt(loadedPvtKey, encryptedDataRead)
//
//    writeTextToFile("decrypted_data", (decryptedData.map(_.toChar)).mkString )

  }
}
