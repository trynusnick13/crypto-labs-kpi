import hashlib
from typing import Optional, Tuple, cast

from Crypto.Cipher import AES, PKCS1_OAEP
from Crypto.Cipher._mode_cfb import CfbMode
from Crypto.Random import get_random_bytes
from Crypto.PublicKey import RSA

from config import AES_KEY

def hash_sha1(message: str):
    hash_object = hashlib.sha1(message.encode())

    return hash_object.digest()

def generate_rsa_keys() -> Tuple[bytes, bytes]:
    key = RSA.generate(2048)
    private_key = key.export_key()
    public_key = key.publickey().export_key()

    return public_key, private_key

def encrypt_rsa(key: bytes, data: bytes):
    cipher_rsa = PKCS1_OAEP.new(RSA.import_key(key))
    encrypted_data = cipher_rsa.encrypt(data)

    return encrypted_data


def decrypt_rsa(key: bytes, data: bytes):
    cipher_rsa = PKCS1_OAEP.new(RSA.import_key(key))
    decrypted_data = cipher_rsa.decrypt(data)

    return decrypted_data


def generate_aes_key(key: bytes, iv: Optional[bytes] = None) -> Tuple[CfbMode, bytes]:
    if iv is None:
        cipher = AES.new(key, AES.MODE_CFB)
    else:
        cipher = AES.new(key, AES.MODE_CFB, iv=iv)

    cipher = cast(CfbMode, cipher)

    return cipher, cipher.iv


def decrypt_aes(key: bytes, iv: bytes, message: bytes):
    cipher = AES.new(key, AES.MODE_CFB, iv=iv)

    return cipher.decrypt(message)


def main():
    init_msg = "IASA is the Best"
    hashed_msg: bytes = hash_sha1(init_msg)
    aes_key, init_vector = generate_aes_key(AES_KEY)
    encrypted_hash = aes_key.encrypt(hashed_msg)
    print(aes_key)

    rsa_pub_key, rsa_pvt_key = generate_rsa_keys()
    encrypted_aes = encrypt_rsa(rsa_pub_key, AES_KEY)
    #####################################################

    received_msg = "IASA is the Best"
    decrypted_aes = decrypt_rsa(rsa_pvt_key, encrypted_aes)
    decrypted_hash = decrypt_aes(decrypted_aes, init_vector, encrypted_hash)
    print(hash_sha1(received_msg))
    print(decrypted_hash)


main()
