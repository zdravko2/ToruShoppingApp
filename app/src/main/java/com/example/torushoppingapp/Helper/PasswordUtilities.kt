import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordUtilities {

    private const val SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    private const val SALT = "salt"

    fun generateSalt(length: Int = 16): String {
        val random = SecureRandom()
        val salt = StringBuilder()
        repeat(length) {
            val randomIndex = random.nextInt(SALT_CHARS.length)
            salt.append(SALT_CHARS[randomIndex])
        }
        return salt.toString()
    }

    fun hashPassword(password: String, iterations: Int = 65536, keyLength: Int = 256): String {
        val saltBytes = SALT.toByteArray(Charsets.UTF_8)
        val spec = PBEKeySpec(password.toCharArray(), saltBytes, iterations, keyLength)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hashBytes = factory.generateSecret(spec).encoded

        val hexString = StringBuilder()
        for (byte in hashBytes) {
            val hex = String.format("%02x", byte)
            hexString.append(hex)
        }

        return hexString.toString()
    }
}