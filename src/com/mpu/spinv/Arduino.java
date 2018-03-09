package com.mpu.spinv;

import br.pro.turing.javino.Javino;

public class Arduino {
	public static String bpmtest, tempTest, grooveTest;

	public Arduino() {
		Excel exl = new Excel();

		Javino javino = new Javino();

		String port = "COM4";

		String ask = "";

		//while (ask != "never") {
			for (int i = 0; i < 900; i++) {
			exl.plan();

			ask = "bpm";

			if (javino.requestData(port, ask)) {
				bpmtest = javino.getData();

			}

			ask = "temp";

			if (javino.requestData(port, ask)) {
				tempTest = javino.getData();

			}

			ask = "suor";

			if (javino.requestData(port, ask)) {
				grooveTest = javino.getData();

			}
			exl.escrever();
		}

		// }
	}
}
