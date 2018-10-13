function func1(j) {
	var window={};
	window.dict_pagetoken="$$pagetoken$$";
	function RotateLeft(a, b) {
		return (a << b) | (a >>> (32 - b))
	}
	function AddUnsigned(a, b) {
		var c, lY4, lX8, lY8, lResult;
		lX8 = (a & 0x80000000);
		lY8 = (b & 0x80000000);
		c = (a & 0x40000000);
		lY4 = (b & 0x40000000);
		lResult = (a & 0x3FFFFFFF) + (b & 0x3FFFFFFF);
		if (c & lY4) {
			return (lResult ^ 0x80000000 ^ lX8 ^ lY8)
		}
		if (c | lY4) {
			if (lResult & 0x40000000) {
				return (lResult ^ 0xC0000000 ^ lX8 ^ lY8)
			} else {
				return (lResult ^ 0x40000000 ^ lX8 ^ lY8)
			}
		} else {
			return (lResult ^ lX8 ^ lY8)
		}
	}
	function F(x, y, z) {
		return (x & y) | ((~x) & z)
	}
	function G(x, y, z) {
		return (x & z) | (y & (~z))
	}
	function H(x, y, z) {
		return (x ^ y ^ z)
	}
	function I(x, y, z) {
		return (y ^ (x | (~z)))
	}
	function FF(a, b, c, d, x, s, e) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), e));
		return AddUnsigned(RotateLeft(a, s), b)
	};

	function GG(a, b, c, d, x, s, e) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), e));
		return AddUnsigned(RotateLeft(a, s), b)
	};

	function HH(a, b, c, d, x, s, e) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), e));
		return AddUnsigned(RotateLeft(a, s), b)
	};

	function II(a, b, c, d, x, s, e) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), e));
		return AddUnsigned(RotateLeft(a, s), b)
	};

	function ConvertToWordArray(a) {
		var b;
		var c = a.length;
		var d = c + 8;
		var e = (d - (d % 64)) / 64;
		var f = (e + 1) * 16;
		var g = Array(f - 1);
		var h = 0;
		var i = 0;
		while (i < c) {
			b = (i - (i % 4)) / 4;
			h = (i % 4) * 8;
			g[b] = (g[b] | (a.charCodeAt(i) << h));
			i++
		}
		b = (i - (i % 4)) / 4;
		h = (i % 4) * 8;
		g[b] = g[b] | (0x80 << h);
		g[f - 2] = c << 3;
		g[f - 1] = c >>> 29;
		return g
	};

	function WordToHex(a) {
		var b = "",
			WordToHexValue_temp = "",
			lByte, lCount;
		for (lCount = 0; lCount <= 3; lCount++) {
			lByte = (a >>> (lCount * 8)) & 255;
			WordToHexValue_temp = "0" + lByte.toString(16);
			b = b + WordToHexValue_temp.substr(WordToHexValue_temp.length - 2, 2)
		}
		return b
	};

	function Utf8Encode(a) {
		a = a.replace(/\r\n/g, "\n");
		var b = "";
		for (var n = 0; n < a.length; n++) {
			var c = a.charCodeAt(n);
			if (c < 128) {
				b += String.fromCharCode(c)
			} else if ((c > 127) && (c < 2048)) {
				b += String.fromCharCode((c >> 6) | 192);
				b += String.fromCharCode((c & 63) | 128)
			} else {
				b += String.fromCharCode((c >> 12) | 224);
				b += String.fromCharCode(((c >> 6) & 63) | 128);
				b += String.fromCharCode((c & 63) | 128)
			}
		}
		b += String.fromCharCode(100, 105, 99, 116, 99, 110);
		if (window.dict_pagetoken) b += window.dict_pagetoken;
		return b
	};
	var x = Array();
	var k, AA, BB, CC, DD, a, b, c, d;
	var l = 7,
		S12 = 12,
		S13 = 17,
		S14 = 22;
	var m = 5,
		S22 = 9,
		S23 = 14,
		S24 = 20;
	var o = 4,
		S32 = 11,
		S33 = 16,
		S34 = 23;
	var p = 6,
		S42 = 10,
		S43 = 15,
		S44 = 21;
	j = Utf8Encode(j);
	x = ConvertToWordArray(j);
	a = 0x67452301;
	b = 0xEFCDAB89;
	c = 0x98BADCFE;
	d = 0x10325476;
	for (k = 0; k < x.length; k += 16) {
		AA = a;
		BB = b;
		CC = c;
		DD = d;
		a = FF(a, b, c, d, x[k + 0], l, 0xD76AA478);
		d = FF(d, a, b, c, x[k + 1], S12, 0xE8C7B756);
		c = FF(c, d, a, b, x[k + 2], S13, 0x242070DB);
		b = FF(b, c, d, a, x[k + 3], S14, 0xC1BDCEEE);
		a = FF(a, b, c, d, x[k + 4], l, 0xF57C0FAF);
		d = FF(d, a, b, c, x[k + 5], S12, 0x4787C62A);
		c = FF(c, d, a, b, x[k + 6], S13, 0xA8304613);
		b = FF(b, c, d, a, x[k + 7], S14, 0xFD469501);
		a = FF(a, b, c, d, x[k + 8], l, 0x698098D8);
		d = FF(d, a, b, c, x[k + 9], S12, 0x8B44F7AF);
		c = FF(c, d, a, b, x[k + 10], S13, 0xFFFF5BB1);
		b = FF(b, c, d, a, x[k + 11], S14, 0x895CD7BE);
		a = FF(a, b, c, d, x[k + 12], l, 0x6B901122);
		d = FF(d, a, b, c, x[k + 13], S12, 0xFD987193);
		c = FF(c, d, a, b, x[k + 14], S13, 0xA679438E);
		b = FF(b, c, d, a, x[k + 15], S14, 0x49B40821);
		a = GG(a, b, c, d, x[k + 1], m, 0xF61E2562);
		d = GG(d, a, b, c, x[k + 6], S22, 0xC040B340);
		c = GG(c, d, a, b, x[k + 11], S23, 0x265E5A51);
		b = GG(b, c, d, a, x[k + 0], S24, 0xE9B6C7AA);
		a = GG(a, b, c, d, x[k + 5], m, 0xD62F105D);
		d = GG(d, a, b, c, x[k + 10], S22, 0x2441453);
		c = GG(c, d, a, b, x[k + 15], S23, 0xD8A1E681);
		b = GG(b, c, d, a, x[k + 4], S24, 0xE7D3FBC8);
		a = GG(a, b, c, d, x[k + 9], m, 0x21E1CDE6);
		d = GG(d, a, b, c, x[k + 14], S22, 0xC33707D6);
		c = GG(c, d, a, b, x[k + 3], S23, 0xF4D50D87);
		b = GG(b, c, d, a, x[k + 8], S24, 0x455A14ED);
		a = GG(a, b, c, d, x[k + 13], m, 0xA9E3E905);
		d = GG(d, a, b, c, x[k + 2], S22, 0xFCEFA3F8);
		c = GG(c, d, a, b, x[k + 7], S23, 0x676F02D9);
		b = GG(b, c, d, a, x[k + 12], S24, 0x8D2A4C8A);
		a = HH(a, b, c, d, x[k + 5], o, 0xFFFA3942);
		d = HH(d, a, b, c, x[k + 8], S32, 0x8771F681);
		c = HH(c, d, a, b, x[k + 11], S33, 0x6D9D6122);
		b = HH(b, c, d, a, x[k + 14], S34, 0xFDE5380C);
		a = HH(a, b, c, d, x[k + 1], o, 0xA4BEEA44);
		d = HH(d, a, b, c, x[k + 4], S32, 0x4BDECFA9);
		c = HH(c, d, a, b, x[k + 7], S33, 0xF6BB4B60);
		b = HH(b, c, d, a, x[k + 10], S34, 0xBEBFBC70);
		a = HH(a, b, c, d, x[k + 13], o, 0x289B7EC6);
		d = HH(d, a, b, c, x[k + 0], S32, 0xEAA127FA);
		c = HH(c, d, a, b, x[k + 3], S33, 0xD4EF3085);
		b = HH(b, c, d, a, x[k + 6], S34, 0x4881D05);
		a = HH(a, b, c, d, x[k + 9], o, 0xD9D4D039);
		d = HH(d, a, b, c, x[k + 12], S32, 0xE6DB99E5);
		c = HH(c, d, a, b, x[k + 15], S33, 0x1FA27CF8);
		b = HH(b, c, d, a, x[k + 2], S34, 0xC4AC5665);
		a = II(a, b, c, d, x[k + 0], p, 0xF4292244);
		d = II(d, a, b, c, x[k + 7], S42, 0x432AFF97);
		c = II(c, d, a, b, x[k + 14], S43, 0xAB9423A7);
		b = II(b, c, d, a, x[k + 5], S44, 0xFC93A039);
		a = II(a, b, c, d, x[k + 12], p, 0x655B59C3);
		d = II(d, a, b, c, x[k + 3], S42, 0x8F0CCC92);
		c = II(c, d, a, b, x[k + 10], S43, 0xFFEFF47D);
		b = II(b, c, d, a, x[k + 1], S44, 0x85845DD1);
		a = II(a, b, c, d, x[k + 8], p, 0x6FA87E4F);
		d = II(d, a, b, c, x[k + 15], S42, 0xFE2CE6E0);
		c = II(c, d, a, b, x[k + 6], S43, 0xA3014314);
		b = II(b, c, d, a, x[k + 13], S44, 0x4E0811A1);
		a = II(a, b, c, d, x[k + 4], p, 0xF7537E82);
		d = II(d, a, b, c, x[k + 11], S42, 0xBD3AF235);
		c = II(c, d, a, b, x[k + 2], S43, 0x2AD7D2BB);
		b = II(b, c, d, a, x[k + 9], S44, 0xEB86D391);
		a = AddUnsigned(a, AA);
		b = AddUnsigned(b, BB);
		c = AddUnsigned(c, CC);
		d = AddUnsigned(d, DD)
	}
	var q = WordToHex(a) + WordToHex(b) + WordToHex(c) + WordToHex(d);
	return q.toLowerCase()
};
func1("$$src$$");