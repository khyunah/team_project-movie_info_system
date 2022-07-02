package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.ActorDao;
import dao.MovieInfoDao;
import dao.StaffInfoDao;
import dto.ActorInfoDto;
import dto.MovieInfoDto;
import dto.StaffInfoDto;

public class MovieFrame extends JFrame implements ActionListener {

	// 영화, 배우, 스태프 기능 DAO
	private MovieInfoDao movieDao;
	private ActorDao actorDao;
	private StaffInfoDao staffDao;

	private JPanel mainpanel;
	private JTabbedPane jtab;

	private JPanel searchPanel;

	// 영화 패널 부분
	private JPanel moviePanel;
	private JLabel lblMovieInfo;
	private MovieInfoPanel movieInfoPanel = new MovieInfoPanel();

	private ScrollPane movieScrollPane;
	private JTextArea movieTextArea;
	private JTextField fldSearchMovie;

	private JButton btnMovieSearch;
	private JButton btnAllMovieSearch;
	private JButton btnDeleteMovie;
	private JButton btnUpdateMovie;
	private JButton btnInsertMovie;

	private Vector<MovieInfoDto> vcMovie = new Vector<>();
	private JList<MovieInfoDto> movieInfoJList = new JList<>();

	// 배우 패널 부분
	private JPanel actorPanel;
	private JLabel lblActorInfo;
	private ActorInfoPanel actorInfoPanel = new ActorInfoPanel();

	private ScrollPane actorScrollPane;
	private JTextField fldSearchActor;

	private JButton btnInsertActor;
	private JButton btnUpdateActor;
	private JButton btnDeleteActor;
	private JButton btnSearchActor;
	private JButton btnAllActorSearch;

	private Vector<ActorInfoDto> vcActor = new Vector<>();
	private JList<ActorInfoDto> actorJList = new JList<>();

	// 스태프 패널 부분
	private JPanel staffPanel;
	private JLabel lblStaffInfo;
	private StaffInfoPanel staffInfoPanel = new StaffInfoPanel();

	private ScrollPane staffScrollPane;
	private JTextField fldSearchStaff;

	private JButton btnSearchStaff;
	private JButton btnAllStaffSearch;
	private JButton btnInsertStaff;
	private JButton btnUpdateStaff;
	private JButton btnDeleteStaff;

	private Vector<StaffInfoDto> vcStaff = new Vector<>();
	private JList<StaffInfoDto> staffJlist = new JList<>();

	private int movieinfoNum;
	private int actcorNum;
	private int staffinfoNum;
	private int personInfoNum;

	public MovieFrame() {
		initData();
		addEventlistener();
	}

	private void initData() {
		setTitle("Movie Information Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 850, 850);
		setResizable(false);

		mainpanel = new JPanel();
		mainpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainpanel.setSize(getWidth(), getHeight());
		mainpanel.setBackground(Color.WHITE);
		mainpanel.setLayout(null);
		setContentPane(mainpanel);

		jtab = new JTabbedPane(JTabbedPane.TOP);
		jtab.setBounds(15, 5, 800, 800);
		mainpanel.add(jtab);

		searchPanel = new JPanel();
		jtab.addTab("Main", null, searchPanel, null);
		searchPanel.setLayout(null);

		// 영화 부분
		moviePanel = new JPanel();
		moviePanel.setBounds(23, 23, 747, 332);
		searchPanel.add(moviePanel);
		moviePanel.setLayout(null);

		lblMovieInfo = new JLabel("Movie Info");
		lblMovieInfo.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblMovieInfo.setBounds(12, 10, 168, 27);
		moviePanel.add(lblMovieInfo);

		fldSearchMovie = new JTextField();
		fldSearchMovie.setFont(new Font("나눔고딕", Font.BOLD, 13));
		fldSearchMovie.setBounds(12, 47, 287, 25);
		fldSearchMovie.setText("영화 이름 검색");
		moviePanel.add(fldSearchMovie);

		movieScrollPane = new ScrollPane();
		movieScrollPane.setBounds(12, 84, 577, 226);
		moviePanel.add(movieScrollPane);

		movieTextArea = new JTextArea();
		movieTextArea.setBounds(12, 88, 573, 222);
		movieScrollPane.add(movieTextArea);

		movieInfoJList = new JList<>();
		movieInfoJList.setBounds(12, 88, 573, 222);
		movieTextArea.add(movieInfoJList);

		btnMovieSearch = new JButton("Search");
		btnMovieSearch.setBounds(622, 48, 100, 25);
		btnMovieSearch.setBackground(Color.WHITE);
		moviePanel.add(btnMovieSearch);

		btnAllMovieSearch = new JButton("Search All");
		btnAllMovieSearch.setBounds(622, 88, 100, 25);
		btnAllMovieSearch.setBackground(Color.WHITE);
		moviePanel.add(btnAllMovieSearch);

		btnInsertMovie = new JButton("Insert");
		btnInsertMovie.setBounds(622, 134, 100, 25);
		btnInsertMovie.setBackground(Color.WHITE);
		moviePanel.add(btnInsertMovie);

		btnUpdateMovie = new JButton("Update");
		btnUpdateMovie.setBounds(622, 179, 100, 25);
		btnUpdateMovie.setBackground(Color.WHITE);
		moviePanel.add(btnUpdateMovie);

		btnDeleteMovie = new JButton("Delete");
		btnDeleteMovie.setBounds(622, 224, 100, 25);
		btnDeleteMovie.setBackground(Color.WHITE);
		moviePanel.add(btnDeleteMovie);

		// 배우 부분
		actorPanel = new JPanel();
		actorPanel.setBounds(23, 407, 342, 332);
		searchPanel.add(actorPanel);
		actorPanel.setLayout(null);

		fldSearchActor = new JTextField();
		fldSearchActor.setText("배우 이름 검색");
		fldSearchActor.setBounds(38, 67, 106, 21);
		actorPanel.add(fldSearchActor);
		fldSearchActor.setColumns(10);

		actorScrollPane = new ScrollPane();
		actorScrollPane.setBounds(38, 98, 253, 151);
		actorPanel.add(actorScrollPane);

		lblActorInfo = new JLabel("Actor Info");
		lblActorInfo.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblActorInfo.setBounds(38, 21, 147, 36);
		actorPanel.add(lblActorInfo);

		actorJList = new JList<>();
		actorJList.setBounds(38, 98, 253, 151);
		actorScrollPane.add(actorJList);

		btnAllActorSearch = new JButton("Search All");
		btnAllActorSearch.setBackground(Color.WHITE);
		btnAllActorSearch.setBounds(38, 266, 100, 21);
		actorPanel.add(btnAllActorSearch);

		btnInsertActor = new JButton("Insert");
		btnInsertActor.setBackground(Color.WHITE);
		btnInsertActor.setBounds(191, 266, 100, 21);
		actorPanel.add(btnInsertActor);

		btnUpdateActor = new JButton("Update");
		btnUpdateActor.setBackground(Color.WHITE);
		btnUpdateActor.setBounds(191, 293, 100, 21);
		actorPanel.add(btnUpdateActor);

		btnDeleteActor = new JButton("Delete");
		btnDeleteActor.setBackground(Color.WHITE);
		btnDeleteActor.setBounds(38, 293, 100, 21);
		actorPanel.add(btnDeleteActor);

		btnSearchActor = new JButton("Search");
		btnSearchActor.setBackground(Color.WHITE);
		btnSearchActor.setBounds(191, 67, 100, 21);
		actorPanel.add(btnSearchActor);

		// 스태프 부분
		staffPanel = new JPanel();
		staffPanel.setBounds(428, 407, 342, 332);
		searchPanel.add(staffPanel);
		staffPanel.setLayout(null);

		lblStaffInfo = new JLabel("Staff Info");
		lblStaffInfo.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblStaffInfo.setBounds(38, 21, 250, 36);
		staffPanel.add(lblStaffInfo);

		staffScrollPane = new ScrollPane();
		staffScrollPane.setBounds(38, 98, 253, 151);
		staffPanel.add(staffScrollPane);

		fldSearchStaff = new JTextField();
		fldSearchStaff.setText("스태프 이름 검색");
		fldSearchStaff.setColumns(10);
		fldSearchStaff.setBounds(38, 67, 106, 21);
		staffPanel.add(fldSearchStaff);

		btnSearchStaff = new JButton("Search");
		btnSearchStaff.setBackground(Color.WHITE);
		btnSearchStaff.setBounds(191, 67, 100, 21);
		staffPanel.add(btnSearchStaff);

		staffJlist = new JList<>();
		staffJlist.setBounds(38, 98, 253, 151);
		staffScrollPane.add(staffJlist);

		btnAllStaffSearch = new JButton("Search All");
		btnAllStaffSearch.setBackground(Color.WHITE);
		btnAllStaffSearch.setBounds(38, 266, 100, 21);
		staffPanel.add(btnAllStaffSearch);

		btnInsertStaff = new JButton("Insert");
		btnInsertStaff.setBackground(Color.WHITE);
		btnInsertStaff.setBounds(191, 266, 100, 21);
		staffPanel.add(btnInsertStaff);

		btnUpdateStaff = new JButton("Update");
		btnUpdateStaff.setBackground(Color.WHITE);
		btnUpdateStaff.setBounds(191, 293, 100, 21);
		staffPanel.add(btnUpdateStaff);

		btnDeleteStaff = new JButton("Delete");
		btnDeleteStaff.setBackground(Color.WHITE);
		btnDeleteStaff.setBounds(38, 293, 100, 21);
		staffPanel.add(btnDeleteStaff);

		setVisible(true);

	}

	private void addEventlistener() {
		// 영화정보 관련 이벤트
		btnMovieSearch.addActionListener(this);
		btnAllMovieSearch.addActionListener(this);
		btnInsertMovie.addActionListener(this);
		btnUpdateMovie.addActionListener(this);
		btnDeleteMovie.addActionListener(this);
		movieInfoPanel.getBtnInsertMovieInfo().addActionListener(this);
		movieInfoPanel.getBtnUpdateMovieInfo().addActionListener(this);
		fldSearchMovie.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (fldSearchMovie.getText().equals("영화 이름 검색")) {
					fldSearchMovie.setText(null);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (fldSearchMovie.getText().equals("")) {
					fldSearchMovie.setText("영화 이름 검색");
				}
			}
		});
		movieInfoJList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					MovieInfoDto dto = movieInfoJList.getSelectedValue();
					new MovieInfoDetailFrame(dto);

					// 선택되어진 밸류 없애는 코드
					movieInfoJList.setSelectedValue(null, false);
				}
			}
		});

		// 배우 정보 관련 이벤트
		btnAllActorSearch.addActionListener(this);
		btnSearchActor.addActionListener(this);
		btnUpdateActor.addActionListener(this);
		btnDeleteActor.addActionListener(this);
		btnInsertActor.addActionListener(this);
		actorInfoPanel.getBtnInsertActorInfo().addActionListener(this);
		actorInfoPanel.getBtnUpdateActorInfo().addActionListener(this);
		fldSearchActor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (fldSearchActor.getText().equals("배우 이름 검색")) {
					fldSearchActor.setText(null);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (fldSearchActor.getText().equals("")) {
					fldSearchActor.setText("배우 이름 검색");
				}
			}
		});
		actorJList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					ActorInfoDto dto = actorJList.getSelectedValue();
					new ActorInforDetailFrame(dto);
					actorJList.setSelectedValue(null, false);
				}
			}
		});

		// 스태프 정보 관련 이벤트
		btnSearchStaff.addActionListener(this);
		btnAllStaffSearch.addActionListener(this);
		btnDeleteStaff.addActionListener(this);
		btnUpdateStaff.addActionListener(this);
		btnInsertStaff.addActionListener(this);
		staffInfoPanel.getBtnUpdateStaffInfo().addActionListener(this);
		staffInfoPanel.getBtnInsertStaffInfo().addActionListener(this);
		fldSearchStaff.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (fldSearchStaff.getText().equals("스태프 이름 검색")) {
					fldSearchStaff.setText(null);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (fldSearchStaff.getText().equals("")) {
					fldSearchStaff.setText("스태프 이름 검색");
				}
			}
		});
		staffJlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					StaffInfoDto dto = staffJlist.getSelectedValue();
					new StaffInfoDetailFrame(dto);
					staffJlist.setSelectedValue(null, false);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 기능 DAO
		movieDao = new MovieInfoDao();
		actorDao = new ActorDao();
		staffDao = new StaffInfoDao();

		// Movie Search 버튼
		if (e.getSource() == btnMovieSearch) {

			if (fldSearchMovie.getText().equals("영화 이름 검색")) {
				JOptionPane.showMessageDialog(null, "영화이름을 입력해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
			} else {

				// 현재 리스트에 있는 데이터 없애기
				vcMovie.removeAllElements();

				// 입력한 영화제목으로 검색하기
				String removeTrimTitle = fldSearchMovie.getText().replace(" ", "");
				Vector<MovieInfoDto> selectMoviTitleResult = movieDao.selectMovieTitle(removeTrimTitle);

				for (int i = 0; i < selectMoviTitleResult.size(); i++) {
					vcMovie.add(selectMoviTitleResult.get(i));
				}

				movieInfoJList.setListData(vcMovie);
				movieScrollPane.add(movieInfoJList);

				movieInfoJList.setSelectedValue(null, false);
			}
		}

		// Movie Search All 버튼
		else if (e.getSource() == btnAllMovieSearch) {

			vcMovie.removeAllElements();

			Vector<MovieInfoDto> selectAllMovieInfoResult = movieDao.selectAllMovieInfo();

			for (int i = 0; i < selectAllMovieInfoResult.size(); i++) {
				vcMovie.add(selectAllMovieInfoResult.get(i));
			}

			movieInfoJList.setListData(vcMovie);
			movieScrollPane.add(movieInfoJList);

			movieInfoJList.setSelectedValue(null, false);
		}

		// Movie Insert 버튼
		else if (e.getSource() == btnInsertMovie) {
			
			resetMovieInfoTextField();

			if(jtab.getTabCount() == 2) {
				jtab.removeTabAt(1);
			}

			jtab.addTab("Moive", null, movieInfoPanel, null);

			// 해당 탭으로 이동
			jtab.setSelectedComponent(movieInfoPanel);

			movieInfoPanel.getBtnUpdateMovieInfo().setEnabled(false);
			movieInfoPanel.getBtnInsertMovieInfo().setEnabled(true);

			movieInfoJList.setSelectedValue(null, false);

		}

		// Movie Update 버튼
		else if (e.getSource() == btnUpdateMovie) {

			if (movieInfoJList.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "수정하려는 영화 항목을 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else if (movieInfoJList.getSelectedValue() != null) {

				movieInfoPanel.getBtnUpdateMovieInfo().setEnabled(true);
				movieInfoPanel.getBtnInsertMovieInfo().setEnabled(false);

				MovieInfoDto dto = movieInfoJList.getSelectedValue();

				movieInfoPanel.getFldMovieTitle().setText(dto.getMovieTitle());
				movieInfoPanel.getFldDirectorName().setText(dto.getDirectorName());

				movieInfoPanel.getFldTotalIncome().setText(String.valueOf(dto.getTotalIncome()));
				movieInfoPanel.getFldAudience().setText(String.valueOf(dto.getAudience()));
				movieInfoPanel.getFldRating().setText(String.valueOf(dto.getRating()));

				movieInfoPanel.getFldReleaseYear().setText(String.valueOf(dto.getReleaseYear()));
				movieInfoPanel.getFldReleaseMonth().setText(String.valueOf(dto.getReleaseMonth()));

				movieInfoPanel.getFldMoviePlot().setText(dto.getMoviePlot());
				movieInfoPanel.getFldReview1().setText(dto.getReview1());
				movieInfoPanel.getFldReview2().setText(dto.getReview2());
				movieInfoPanel.getFldReview3().setText(dto.getReview3());

				movieinfoNum = dto.getMovieInfoNum();

				movieInfoJList.setSelectedValue(null, false);
				
				if(jtab.getTabCount() == 2) {
					jtab.removeTabAt(1);
				}

				jtab.addTab("Movie", null, movieInfoPanel, null);
				jtab.setSelectedComponent(movieInfoPanel);
			}
		}

		// Movie Delete 버튼
		else if (e.getSource() == btnDeleteMovie) {

			if (movieInfoJList.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "삭제하려는 항목을 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else {

				if (vcMovie.size() != 0) {

					int movieinfoNum = movieInfoJList.getSelectedValue().getMovieInfoNum();

					// DB에서 데이터 삭제
					int deleteCheck = movieDao.deleteMovieInfo(movieinfoNum);

					if (deleteCheck == 1) {

						// 보여지는 화면 JList에서 삭제
						int index = movieInfoJList.getSelectedIndex();
						vcMovie.remove(index);
						movieInfoJList.repaint();

						JOptionPane.showMessageDialog(null, "영화 정보 삭제가 완료되었습니다.", "INFORMATION",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "영화 정보 삭제가 정상적으로 처리 되지 않았습니다.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			movieInfoJList.setSelectedValue(null, false);
		}

		// MovieInfoPanel - 영화정보 등록하기 버튼
		else if (e.getSource() == movieInfoPanel.getBtnInsertMovieInfo()) {
			
//			&& !movieInfoPanel.getFldDirectorName().getText().equals("")
//			&& !movieInfoPanel.getFldTotalIncome().getText().equals("")
//			&& !movieInfoPanel.getFldAudience().getText().equals("")
//			&& !movieInfoPanel.getFldRating().getText().equals("")
//			&& !movieInfoPanel.getFldReleaseYear().getText().equals("")
			// 패널에 있는 텍스트 필드에 전부 입력을 했는지 체크하는 과정
			
			if (!movieInfoPanel.getFldMovieTitle().getText().equals("")

					&& !movieInfoPanel.getFldReleaseMonth().getText().equals("")) {

				String removeTrimMovieTitle = removeTrim(movieInfoPanel.getFldMovieTitle().getText());
				String removeTrimDirectorName = removeTrim(movieInfoPanel.getFldDirectorName().getText());
				
				boolean doubleCheck = movieDao.selectMovieDoubleCheck(removeTrimMovieTitle,	removeTrimDirectorName);

				// 중복이 아닐때 true
				if (doubleCheck) {

					MovieInfoDto dto = new MovieInfoDto();
					addDtoMovieInfo(dto);
					int insertCheck = movieDao.insertMovieInfo(dto);

					if (insertCheck == 1) {
						JOptionPane.showMessageDialog(null, "영화 정보 등록이 정상적으로 완료되었습니다.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "영화 정보 등록이 정상적으로 처리되지 않았습니다.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "입력하신 정보의 영화가 이미 존재합니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "영화이름, 감독, 매출액, 관객수, 평점, 개봉연도, 개봉월은 빈칸없이 입력해주세요.", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// MovieInfoPanel - 영화정보 수정하기 버튼
		else if (e.getSource() == movieInfoPanel.getBtnUpdateMovieInfo()) {

			if (!movieInfoPanel.getFldMovieTitle().getText().equals("")
					&& !movieInfoPanel.getFldDirectorName().getText().equals("")
					&& !movieInfoPanel.getFldTotalIncome().getText().equals("")
					&& !movieInfoPanel.getFldAudience().getText().equals("")
					&& !movieInfoPanel.getFldRating().getText().equals("")
					&& !movieInfoPanel.getFldReleaseYear().getText().equals("")
					&& !movieInfoPanel.getFldReleaseMonth().getText().equals("")) {

				MovieInfoDto dto = new MovieInfoDto();
				addDtoMovieInfo(dto);
				int updateCheck = movieDao.updateMovieInfo(movieinfoNum, dto);

				if (updateCheck == 1) {
					JOptionPane.showMessageDialog(null, "영화 정보 업데이트가 정상적으로 완료되었습니다.", "INFORMATION",
							JOptionPane.INFORMATION_MESSAGE);
					resetMovieInfoTextField();

				} else {
					JOptionPane.showMessageDialog(null, "영화 정보 업데이트가 정상적으로 처리되지 않았습니다", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "영화이름, 감독, 매출액, 관객수, 평점, 개봉연도, 개봉월은 빈칸없이 입력해주세요", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// Actor Search 버튼
		else if (e.getSource() == btnSearchActor) {

			if (fldSearchActor.getText().equals("")) {

				JOptionPane.showMessageDialog(null, "배우 이름을 입력해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else {

				vcActor.removeAllElements();
				
				String removeTrimActorName = removeTrim(fldSearchActor.getText());

				Vector<ActorInfoDto> selectActorInfor = actorDao.selectActorInfor(removeTrimActorName);

				for (int i = 0; i < selectActorInfor.size(); i++) {
					vcActor.add(selectActorInfor.get(i));
				}

				actorJList.setListData(vcActor);
				actorScrollPane.add(actorJList);

				actorJList.setSelectedValue(null, false);
			}
		}

		// Actor Search All 버튼
		else if (e.getSource() == btnAllActorSearch) {

			vcActor.removeAllElements();

			Vector<ActorInfoDto> selectAllActorInfoResult = actorDao.selectAllActorInfor();

			for (int i = 0; i < selectAllActorInfoResult.size(); i++) {
				vcActor.add(selectAllActorInfoResult.get(i));
			}

			actorJList.setListData(vcActor);
			actorScrollPane.add(actorJList);

			actorJList.setSelectedValue(null, false);
		}

		// Actor Insert 버튼
		else if (e.getSource() == btnInsertActor) {

			resetActorInfoTextField();

			actorInfoPanel.getBtnUpdateActorInfo().setEnabled(false);
			actorInfoPanel.getBtnInsertActorInfo().setEnabled(true);
			
			if(jtab.getTabCount() == 2) {
				jtab.removeTabAt(1);
			}

			jtab.addTab("Actor", null, actorInfoPanel, null);
			jtab.setSelectedComponent(actorInfoPanel);

			actorJList.setSelectedValue(null, false);

		}

		// Actor Update 버튼
		else if (e.getSource() == btnUpdateActor) {

			if (actorJList.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "업데이트 항목을 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else if (actorJList.getSelectedValue() != null) {

				actorInfoPanel.getBtnUpdateActorInfo().setEnabled(true);
				actorInfoPanel.getBtnInsertActorInfo().setEnabled(false);

				ActorInfoDto dto = actorJList.getSelectedValue();

				actorInfoPanel.getFldActorName().setText(dto.getActorName());
				actorInfoPanel.getFldActorRepresentativeMovie().setText(dto.getRepresentativeMovie());
				actorInfoPanel.getFldActorRepresentativeRole().setText(dto.getRepresentativeRole());
				actorInfoPanel.getFldActorBirthYear().setText(dto.getBirthYear());
				actorInfoPanel.getFldatorTall().setText(dto.getActorTall());
				actorInfoPanel.getFldActorWieght().setText(dto.getActorWeight());
				actorInfoPanel.getFldActorMarriagePartner().setText(dto.getMarriagePartner());
				actorInfoPanel.getFldActorGender().setText(dto.getGender());

				actcorNum = dto.getActorNum();
				
				if(jtab.getTabCount() == 2) {
					jtab.removeTabAt(1);
				}

				jtab.addTab("Actor", null, actorInfoPanel, null);
				jtab.setSelectedComponent(actorInfoPanel);

				actorJList.setSelectedValue(null, false);
			}
		}

		// Actor Delete 버튼
		else if (e.getSource() == btnDeleteActor) {

			if (actorJList.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "삭제하려는 항목을 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else {

				if (vcActor.size() != 0) {
					
					String removeActorName = removeTrim(actorJList.getSelectedValue().getActorName());
					int actorBirthYear = Integer.parseInt(actorJList.getSelectedValue().getBirthYear());

					boolean doubleCheck = actorDao.selectActorDoubleCheck(removeActorName, actorBirthYear);

					if (!doubleCheck) {

						int deleteCheck = actorDao.deleteActorInfo(actorJList.getSelectedValue().getPersonNum());

						if (deleteCheck == 1) {

							int index = actorJList.getSelectedIndex();
							vcActor.remove(index);
							actorJList.ensureIndexIsVisible(index);
							actorJList.repaint();

							JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "INFORMATION",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "삭제하려는 정보가 존재하지 않습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			actorJList.setSelectedValue(null, false);
		}

		// ActorInfoPanel - 배우정보 등록하기 버튼
		else if (e.getSource() == actorInfoPanel.getBtnInsertActorInfo()) {

			if (!actorInfoPanel.getFldActorName().getText().equals("")
					&& !actorInfoPanel.getFldActorGender().getText().equals("")
					&& !actorInfoPanel.getFldActorBirthYear().getText().equals("")
					&& !actorInfoPanel.getFldatorTall().getText().equals("")
					&& !actorInfoPanel.getFldActorWieght().getText().equals("")) {

				String removeTrimActorName = actorInfoPanel.getFldActorName().getText();
				int actorBirthYear = Integer.parseInt(actorInfoPanel.getFldActorBirthYear().getText());
				
				boolean doubleCheck = actorDao.selectActorDoubleCheck(removeTrimActorName, actorBirthYear);

				if (!doubleCheck) {

					ActorInfoDto dto = new ActorInfoDto();
					addDtoActorInfo(dto);
					int insertCheck = actorDao.insertActorInfo(dto);

					if (insertCheck == 1) {
						JOptionPane.showMessageDialog(null, "배우정보 등록이 완료되었습니다.", "INFORMATION",
								JOptionPane.INFORMATION_MESSAGE);

						resetActorInfoTextField();

					} else {
						JOptionPane.showMessageDialog(null, "배우 정보 등록이 정상적으로 처리되지 않았습니다.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "입력하신 정보의 배우가 이미 존재합니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "배우 이름을 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}

		// ActorInfoPanel - 배우정보 수정하기 버튼
		else if (e.getSource() == actorInfoPanel.getBtnUpdateActorInfo()) {

			ActorInfoDto dto = new ActorInfoDto();
			addDtoActorInfo(dto);
			int updateCheck = actorDao.updateActorInfo(actcorNum, dto);

			if (updateCheck == 1) {
				JOptionPane.showMessageDialog(null, "배우 정보 업데이트가 완료되었습니다.", "INFORMATION",
						JOptionPane.INFORMATION_MESSAGE);
				resetActorInfoTextField();

			} else {
				JOptionPane.showMessageDialog(null, "배우 정보 업데이트가 정상적으로 처리되지 않았습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}

		// Staff Search 버튼
		else if (e.getSource() == btnSearchStaff) {

			if (fldSearchMovie.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "스태프 이름을 입력해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else {

				vcStaff.removeAllElements();

				String removeTrimStaffName = removeTrim(fldSearchStaff.getText());
				
				Vector<StaffInfoDto> selectDirectorNameResult = staffDao.selectDirectorName(removeTrimStaffName);

				for (int i = 0; i < selectDirectorNameResult.size(); i++) {
					vcStaff.add(selectDirectorNameResult.get(i));
				}

				staffJlist.setListData(vcStaff);
				staffScrollPane.add(staffJlist);

				staffJlist.setSelectedValue(null, false);
			}
		}

		// Staff AllSearch 버튼
		else if (e.getSource() == btnAllStaffSearch) {

			vcStaff.removeAllElements();

			Vector<StaffInfoDto> selectAllStaffInfoResult = staffDao.selectAllStaffInfo();

			for (int i = 0; i < selectAllStaffInfoResult.size(); i++) {
				vcStaff.add(selectAllStaffInfoResult.get(i));
			}

			staffJlist.setListData(vcStaff);
			staffScrollPane.add(staffJlist);

			staffJlist.setSelectedValue(null, false);

		}

		// Staff Insert 버튼
		else if (e.getSource() == btnInsertStaff) {

			resetStaffInfoTextField();

			staffInfoPanel.getBtnInsertStaffInfo().setEnabled(true);
			staffInfoPanel.getBtnUpdateStaffInfo().setEnabled(false);

			if(jtab.getTabCount() == 2) {
				jtab.removeTabAt(1);
			}
			
			jtab.addTab("Staff", null, staffInfoPanel, null);
			jtab.setSelectedComponent(staffInfoPanel);

			staffJlist.setSelectedValue(null, false);

		}

		// Staff Update 버튼
		else if (e.getSource() == btnUpdateStaff) {

			if (staffJlist.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "수정하려는 항목을 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else if (staffJlist.getSelectedValue() != null) {

				staffInfoPanel.getBtnUpdateStaffInfo().setEnabled(true);
				staffInfoPanel.getBtnInsertStaffInfo().setEnabled(false);

				StaffInfoDto dto = staffJlist.getSelectedValue();

				staffInfoPanel.getFldDirectorName().setText(dto.getDirectorName());
				staffInfoPanel.getFldGender().setText(dto.getGender());
				staffInfoPanel.getFldBirthYear().setText(dto.getBirthYear() + "");
				staffInfoPanel.getFldMarriagePartner().setText(dto.getMarriagePartner());
				staffInfoPanel.getFldRepresentativeWork().setText(dto.getRepresentativeWork());

				staffinfoNum = dto.getStaffNum();
				personInfoNum = dto.getPersonNum();

				staffJlist.setSelectedValue(null, false);
				
				if(jtab.getTabCount() == 2) {
					jtab.removeTabAt(1);
				}

				jtab.addTab("Staff", null, staffInfoPanel, null);
				jtab.setSelectedComponent(staffInfoPanel);

			}

		}

		// Staff Delete 버튼
		else if (e.getSource() == btnDeleteStaff) {

			if (staffJlist.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(null, "삭제하려는 항목을 선택해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else {

				if (vcStaff.size() != 0) {

					String rempceTrimDirectorName = removeTrim(staffJlist.getSelectedValue().getDirectorName());
					
					boolean doubleCheck = staffDao.selectStaffInfoDoubleCheck(
							rempceTrimDirectorName, staffJlist.getSelectedValue().getBirthYear());

					if (!doubleCheck) {

						int deleteCheck = staffDao.deleteStaffInfo(staffJlist.getSelectedValue().getStaffNum());

						if (deleteCheck == 1) {

							int index = staffJlist.getSelectedIndex();
							vcStaff.remove(index);
							staffJlist.ensureIndexIsVisible(index);
							staffJlist.repaint();

							JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "INFORMATION",
									JOptionPane.INFORMATION_MESSAGE);

						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "삭제하려는 정보가 존재하지 않습니다", "ERROR", JOptionPane.ERROR_MESSAGE);

				}
			}
			staffJlist.setSelectedValue(null, false);
		}

		// StaffInfoPanel - 스태프 정보 추가 부분
		else if (e.getSource() == staffInfoPanel.getBtnInsertStaffInfo()) {

			if (!staffInfoPanel.getFldBirthYear().getText().equals("")
					&& !staffInfoPanel.getFldDirectorName().getText().equals("")
					&& !staffInfoPanel.getFldGender().getText().equals("")
					&& !staffInfoPanel.getFldRepresentativeWork().getText().equals("")) {
				
				String removeTrimDirectorName = removeTrim(staffInfoPanel.getFldDirectorName().getText());

				boolean doubleCheck = staffDao.selectStaffInfoDoubleCheck(removeTrimDirectorName,
						Integer.parseInt(staffInfoPanel.getFldBirthYear().getText()));

				if (doubleCheck) {

					StaffInfoDto dto = new StaffInfoDto();
					addDtoStaffInfo(dto);
					int insertCheck = staffDao.insertStaffInfo(dto);

					if (insertCheck == 1) {
						JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.", "INFORMATION",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(null, "입력하신 감독정보가 이미 존재합니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "빈칸을 전부 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}

		// StaffInfoPanel - 스태프 정보 수정 부분
		else if (e.getSource() == staffInfoPanel.getBtnUpdateStaffInfo()) {

			if (!staffInfoPanel.getFldBirthYear().getText().equals("")
					&& !staffInfoPanel.getFldDirectorName().getText().equals("")
					&& !staffInfoPanel.getFldGender().getText().equals("")
					&& !staffInfoPanel.getFldMarriagePartner().getText().equals("")
					&& !staffInfoPanel.getFldRepresentativeWork().getText().equals("")) {

				StaffInfoDto dto = new StaffInfoDto();
				addDtoStaffInfo(dto);
				int updateCheck = staffDao.updateStaffInfo(staffinfoNum, dto);

				if (updateCheck == 1) {
					JOptionPane.showMessageDialog(null, "업데이트가 완료되었습니다.", "INFORMATION",
							JOptionPane.INFORMATION_MESSAGE);
					resetStaffInfoTextField();

				} else {
					JOptionPane.showMessageDialog(null, "업데이트가 정상적으로 처리되지 않았습니다", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "빈칸을 전부 입력해주세요", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private String removeTrim(String keyword) {
		return keyword.replace(" ", "");
	}

	// MovieInfo 정보를 MovieInfoDto로 밀어 넣는 메소드 ( insert, update 에서 사용 )
	private void addDtoMovieInfo(MovieInfoDto dto) {
		dto.setMovieTitle(movieInfoPanel.getFldMovieTitle().getText());
		dto.setDirectorName(movieInfoPanel.getFldDirectorName().getText());

		dto.setReleaseYear(Integer.parseInt(movieInfoPanel.getFldReleaseYear().getText()));
		dto.setReleaseMonth(Integer.parseInt(movieInfoPanel.getFldReleaseMonth().getText()));

		dto.setMoviePlot(movieInfoPanel.getFldMoviePlot().getText());

		dto.setTotalIncome(Integer.parseInt(movieInfoPanel.getFldTotalIncome().getText()));
		dto.setAudience(Integer.parseInt(movieInfoPanel.getFldAudience().getText()));
		dto.setRating(Float.parseFloat((movieInfoPanel.getFldRating().getText())));

		dto.setReview1(movieInfoPanel.getFldReview1().getText());
		dto.setReview2(movieInfoPanel.getFldReview2().getText());
		dto.setReview3(movieInfoPanel.getFldReview3().getText());
	}

	// ActorInfo 정보를 ActorInfoDto로 밀어 넣는 메소드 ( insert, update 에서 사용 )
	private void addDtoActorInfo(ActorInfoDto dto) {
		dto.setActorNum(actcorNum);
		dto.setActorName(actorInfoPanel.getFldActorName().getText());

		dto.setRepresentativeMovie(actorInfoPanel.getFldActorRepresentativeMovie().getText());
		dto.setRepresentativeRole(actorInfoPanel.getFldActorRepresentativeRole().getText());

		dto.setBirthYear(actorInfoPanel.getFldActorBirthYear().getText());
		dto.setActorTall(actorInfoPanel.getFldatorTall().getText());
		dto.setActorWeight(actorInfoPanel.getFldActorWieght().getText());
		dto.setGender(actorInfoPanel.getFldActorGender().getText());

		dto.setMarriagePartner(actorInfoPanel.getFldActorMarriagePartner().getText());
	}

	// StaffInfo 정보를 StaffInfoDto로 밀어 넣는 메소드 ( insert, update 에서 사용 )
	private void addDtoStaffInfo(StaffInfoDto dto) {
		dto.setStaffNum(staffinfoNum);
		dto.setPersonNum(personInfoNum);
		dto.setDirectorName(staffInfoPanel.getFldDirectorName().getText());

		dto.setGender(staffInfoPanel.getFldGender().getText());
		if (!staffInfoPanel.getFldBirthYear().getText().equals(null)) {
			dto.setBirthYear(Integer.parseInt(staffInfoPanel.getFldBirthYear().getText()));
		}
		dto.setRepresentativeWork(staffInfoPanel.getFldRepresentativeWork().getText());
		dto.setMarriagePartner(staffInfoPanel.getFldMarriagePartner().getText());
	}

	// 입력후 필드 리셋
	private void resetMovieInfoTextField() {
		movieInfoPanel.getFldMovieTitle().setText(null);
		movieInfoPanel.getFldDirectorName().setText(null);

		movieInfoPanel.getFldTotalIncome().setText(null);
		movieInfoPanel.getFldAudience().setText(null);
		movieInfoPanel.getFldRating().setText(null);

		movieInfoPanel.getFldReleaseYear().setText(null);
		movieInfoPanel.getFldReleaseMonth().setText(null);

		movieInfoPanel.getFldMoviePlot().setText(null);
		movieInfoPanel.getFldReview1().setText(null);
		movieInfoPanel.getFldReview2().setText(null);
		movieInfoPanel.getFldReview3().setText(null);
	}

	// 입력후 필드 리셋
	private void resetStaffInfoTextField() {
		staffInfoPanel.getFldDirectorName().setText(null);
		staffInfoPanel.getFldGender().setText(null);
		staffInfoPanel.getFldBirthYear().setText(null);
		staffInfoPanel.getFldRepresentativeWork().setText(null);
		staffInfoPanel.getFldMarriagePartner().setText(null);
	}

	// 입력후 필드 리셋
	private void resetActorInfoTextField() {
		actorInfoPanel.getFldActorName().setText(null);
		actorInfoPanel.getFldActorRepresentativeMovie().setText(null);
		actorInfoPanel.getFldActorRepresentativeRole().setText(null);
		actorInfoPanel.getFldActorBirthYear().setText(null);
		actorInfoPanel.getFldatorTall().setText(null);
		actorInfoPanel.getFldActorWieght().setText(null);
		actorInfoPanel.getFldActorMarriagePartner().setText(null);
		actorInfoPanel.getFldActorGender().setText(null);
	}

	public static void main(String[] args) {
		new MovieFrame();

	}
}
