import java.security.{KeyPairGenerator, PrivateKey, PublicKey, KeyFactory}
import java.security.spec.{X509EncodedKeySpec, PKCS8EncodedKeySpec}
import javax.crypto.Cipher

object Crypto {
  def generateKeyPair(): (PublicKey, PrivateKey) = {
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(Constants.KeySize)
    val keyPair = generator.generateKeyPair()
    val pubKey = keyPair.getPublic
    val pvtKey = keyPair.getPrivate
    // X.509 and PKCS#8
    println(s"Pub format: ${pubKey.getFormat}. Pvt format: ${pvtKey.getFormat}")

    pubKey -> pvtKey // tuple not Map ;-)
  }

  def loadPublicKeyFromFile(fileName: String): PublicKey = {
    val bytes = Utils.readBinary(fileName)
    val keySpec = new X509EncodedKeySpec(bytes)
    val keyFactory = KeyFactory.getInstance("RSA")

    keyFactory.generatePublic(keySpec)
  }

  def loadPrivateKeyFromFile(fileName: String): PrivateKey = {
    val bytes = Utils.readBinary(fileName)
    val keySpec = new PKCS8EncodedKeySpec(bytes)
    val keyFactory = KeyFactory.getInstance("RSA")

    keyFactory.generatePrivate(keySpec)
  }

  def encrypt(publicKey: PublicKey, rawMsg: Array[Byte]): Array[Byte] = {
    val cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    val encryptedMsg = cipher.doFinal(rawMsg)

    encryptedMsg
  }

  def decrypt(privateKey: PrivateKey, encryptedMsg: Array[Byte]): Array[Byte] = {
    val cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.DECRYPT_MODE, privateKey)
    val decryptedMsg = cipher.doFinal(encryptedMsg)

    decryptedMsg
  }
}
