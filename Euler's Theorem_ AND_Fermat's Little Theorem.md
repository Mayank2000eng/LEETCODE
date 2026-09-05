# 🚀 Fermat's Little Theorem & Euler's Theorem

## 1. Problem kya tha? 🤔

Suppose tumhe calculate karna hai:

$$
2^{1000} \mod 13
$$

Obviously, $2^{1000}$ ko directly calculate karna practical nahi hai.

Mathematicians ne observe kiya ki **modulo ke andar powers repeat hone lagti hain**.

### Example: Powers of 2 modulo 5

$$
2^1 \mod 5 = 2
$$

$$
2^2 \mod 5 = 4
$$

$$
2^3 \mod 5 = 8 \mod 5 = 3
$$

$$
2^4 \mod 5 = 16 \mod 5 = 1
$$

Ab:

$$
2^5 \mod 5 = 2
$$

Cycle dobara start:

$$
2,\ 4,\ 3,\ 1,\ 2,\ 4,\ 3,\ 1 \dots
$$

> 💡 **Main Question:**
>
> **Kya hum predict kar sakte hain ki power kab $1$ par wapas aayegi?**

Isi question se **Fermat's Little Theorem** aur **Euler's Theorem** jaise concepts useful bante hain.

---

# 2. Fermat's Little Theorem

Agar $p$ **prime number** hai aur $a$, $p$ se divisible nahi hai:

$$
a^{p-1} \equiv 1 \pmod p
$$

### Example

$$
2^4 \equiv 1 \pmod 5
$$

Because $5$ prime hai.

---

## Lekin ye formula aaya kahan se? 🤔

Numbers dekho:

$$
1,\ 2,\ 3,\ 4
$$

Ab in sabko $2$ se multiply karke mod $5$ lo:

| Original Number | $\times 2 \mod 5$ |
|---|---|
| $1$ | $2$ |
| $2$ | $4$ |
| $3$ | $1$ |
| $4$ | $3$ |

Result:

$$
2,\ 4,\ 1,\ 3
$$

### Notice something? 👀

Pehle numbers:

$$
1,\ 2,\ 3,\ 4
$$

Baad mein:

$$
2,\ 4,\ 1,\ 3
$$

**Same numbers bas order change ho gaya!**

Ye prime number ki wajah se possible hai.

- Zero nahi aata
- Duplicate values nahi banti

---

## Product compare karo

Original numbers ka product:

$$
1 \times 2 \times 3 \times 4
$$

Multiplication ke baad product:

$$
(2 \times 1)(2 \times 2)(2 \times 3)(2 \times 4)
$$

Isko likh sakte hain:

$$
2^4(1 \times 2 \times 3 \times 4)
$$

Modulo $5$ dono products same set represent karte hain:

$$
2^4(1 \times 2 \times 3 \times 4)
\equiv
1 \times 2 \times 3 \times 4
\pmod 5
$$

Common product cancel karne par:

$$
2^4 \equiv 1 \pmod 5
$$

---

## Generalize karo

Agar $p$ prime hai:

$$
a^{p-1} \equiv 1 \pmod p
$$

This is:

> ## 🟢 Fermat's Little Theorem

---

# 3. Problem with Fermat's Theorem

Fermat's theorem sirf **prime modulus** ke liye hai.

For example:

$$
\mod 10
$$

$10$ prime nahi hai.

Toh question:

> **Kya composite numbers ke liye bhi koi similar rule ho sakta hai?**

Euler ne kaha:

> **Haan! 😄**

Lekin composite numbers ke case mein humein sirf un numbers ko consider karna padega jo $n$ ke saath **coprime** hain.

---

# 4. Euler Totient Function $\phi(n)$

Euler Totient Function simply count karta hai:

> **$1$ se $n$ tak kitne numbers $n$ ke saath coprime hain?**

---

## Example

$$
n = 10
$$

Numbers:

$$
1,\ 2,\ 3,\ 4,\ 5,\ 6,\ 7,\ 8,\ 9
$$

$10$ ke saath coprime numbers:

$$
1,\ 3,\ 7,\ 9
$$

Therefore:

$$
\phi(10) = 4
$$

---

## But why only coprime numbers? 🤔

Because Euler theorem ke peeche bhi wahi idea hai:

> **Agar har coprime number ko $a$ se multiply karein, toh kya wahi set dobara milta hai?**

For example:

$$
n = 10
$$

Coprime numbers:

$$
1,\ 3,\ 7,\ 9
$$

Now $a = 3$.

Multiply everything by $3$ and take mod $10$:

$$
3 \times 1 \equiv 3 \pmod{10}
$$

$$
3 \times 3 \equiv 9 \pmod{10}
$$

$$
3 \times 7 \equiv 1 \pmod{10}
$$

$$
3 \times 9 \equiv 7 \pmod{10}
$$

Result:

$$
3,\ 9,\ 1,\ 7
$$

Again, we got exactly the same set:

$$
1,\ 3,\ 7,\ 9
$$

Bas order change hua.

> 💡 **This is the main idea behind Euler's Theorem.**

---

# 5. Euler's Theorem

Agar:

$$
\gcd(a,n) = 1
$$

then:

$$
a^{\phi(n)} \equiv 1 \pmod n
$$

---

## Example

$$
3^{\phi(10)} \mod 10
$$

Humne dekha:

$$
\phi(10) = 4
$$

Therefore:

$$
3^4 \equiv 1 \pmod{10}
$$

Check:

$$
3^4 = 81
$$

$$
81 \mod 10 = 1
$$

Correct! ✅

---

# 6. Fermat aur Euler ka Connection 🔗

Prime number $p$ ke liye:

$$
\phi(p) = p - 1
$$

Kyunki $1$ se $p-1$ tak har number $p$ ke saath coprime hota hai.

Euler's theorem:

$$
a^{\phi(n)} \equiv 1 \pmod n
$$

Agar $n = p$, where $p$ prime:

$$
\phi(p) = p-1
$$

Therefore:

$$
a^{p-1} \equiv 1 \pmod p
$$

So:

> ## 🟢 Fermat's Little Theorem is simply a special case of Euler's Theorem.

---

# 🧠 Final Intuition

## Euler Totient Function

Batata hai:

> **Modulo $n$ ke system mein kitne useful/coprime numbers hain.**

---

## Fermat's Little Theorem

Prime modulus ke case mein:

$$
a^{p-1} \equiv 1 \pmod p
$$

---

## Euler's Theorem

Same idea ko composite numbers tak extend karta hai:

$$
a^{\phi(n)} \equiv 1 \pmod n
$$

---

# 🎯 Real Motivation

In sabka major motivation hai:

> **Huge powers ko easily modulo ke andar reduce karna.**

---

# Example: Huge Power Calculation

Calculate:

$$
2^{1000} \mod 13
$$

Since $13$ prime hai, Fermat's Little Theorem:

$$
2^{12} \equiv 1 \pmod{13}
$$

Ab $1000$ ko $12$ ke terms mein likho:

$$
1000 = 12 \times 83 + 4
$$

Therefore:

$$
2^{1000}
=
2^{12 \times 83 + 4}
$$

Exponent rule use karo:

$$
2^{1000}
=
(2^{12})^{83} \times 2^4
$$

Modulo $13$:

$$
(2^{12})^{83} \times 2^4
\equiv
1^{83} \times 2^4
\pmod{13}
$$

Ab:

$$
2^4 = 16
$$

Aur:

$$
16 \mod 13 = 3
$$

Therefore:

# 🎉 Final Answer

$$
\boxed{2^{1000} \mod 13 = 3}
$$

---

## 💡 Main Idea

Fermat's theorem ne hume bataya ki:

$$
2^{12} \equiv 1 \pmod{13}
$$

Isliye huge exponent $1000$ ko $12$ ke cycles mein reduce kar diya:

$$
1000 \mod 12 = 4
$$

Isliye effectively:

$$
2^{1000}
\equiv
2^4
\pmod{13}
$$

Aur:

$$
2^4 \mod 13 = 3
$$

---

# ⚡ Quick Summary

| Concept | Formula |
|---|---|
| Fermat's Little Theorem | $a^{p-1} \equiv 1 \pmod p$ |
| Euler Totient | $\phi(n)$ = number of integers coprime with $n$ |
| Euler's Theorem | $a^{\phi(n)} \equiv 1 \pmod n$ |
| Prime case | $\phi(p) = p-1$ |
| Connection | Fermat = Euler's special case |

---

> 🧠 **Golden Rule:**
>
> Agar modulus ke saath base coprime hai, toh Euler's Theorem use karke large powers ko $\phi(n)$ ke cycle ke according reduce kiya ja sakta hai.
