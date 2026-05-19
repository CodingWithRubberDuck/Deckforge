--Kort
INSERT INTO card_list (neutral_mana, white_mana, name, card_type, picture, set_name, rule_text, rarity)
VALUES (4, 2, 'The Cheese Stands Alone', 'ENCHANTMENT', 'the-cheese-stands-alone.png', 'Unglued',
        'When you control no permanents other than this enchantment and have no cards in hand, you win the game.', 'RARE');

INSERT INTO card_list (white_mana, name, card_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (2, 'Mesa Chicken', 'CREATURE', 'Bird', 'mesa-chicken.png', 'unglued',
       'Stand up, Flap your arms, Cluck like a chicken: This creature gains flying until end of turn.',
        2, 2, 'COMMON');

INSERT INTO card_list (neutral_mana, blue_mana, name, card_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (2, 1, 'Clam-I-Am', 'CREATURE', 'Clamfolk', 'clam-i-am.png', 'Unglued',
        'If you roll a 3 on a six-sided die, you may reroll that die.', 2, 2, 'COMMON');

INSERT INTO card_list (neutral_mana, white_mana, name, card_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (3, 1, 'Miss Demeanor', 'CREATURE', 'Lady Of Proper Etiquette', 'miss-demeanor.png',
        'Unglued', 'Flying, first strike. At the beginning of each other player''s upkeep, you may compliment that player on their game play. If you don''t, sacrifice this creature.',
        3, 1, 'UNCOMMON');

INSERT INTO card_list (blue_mana, name, card_type, picture, set_name, rule_text, rarity)
VALUES (1, 'Censorship', 'ENCHANTMENT', 'censorship.png', 'Unglued',
        'As this enchantment enters, choose a word. Whenever a player says the chosen word, this enchantment deals 2 damage to that player.',
        'UNCOMMON');

INSERT INTO card_list (neutral_mana, red_mana, name, card_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (1, 1, 'Chicken Egg', 'CREATURE', 'Egg', 'chicken-egg.png', 'Unglued',
        'At the beginning of your upkeep, roll a six-sided die. If you roll a 6, sacrifice this creature and create a 4/4 red Giant Bird creature token.',
        0, 1, 'COMMON');

INSERT INTO card_list (name, card_type, picture, set_name, rule_text, rarity)
VALUES ('Blacker Lotus', 'ARTIFACT', 'blacker-lotus.png', 'Unglued',
        'Tear this artifact into pieces. Add four mana of any one color. Remove the pieces from the game.', 'RARE');

INSERT INTO card_list (neutral_mana, name, card_type, multi_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (4, 'Paper Tiger', 'ARTIFACT', 'CREATURE', 'paper-tiger.png', 'Unglued',
        'Creatures named Rock Lobster can''t attack or block.', 4, 3, 'COMMON');

INSERT INTO card_list (neutral_mana, name, card_type, multi_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (4, 'Rock Lobster', 'ARTIFACT', 'CREATURE', 'Lobster', 'rock-lobster.png', 'Unglued',
        'Creatures named Scissors Lizard can''t attack or block.', 4, 3, 'COMMON');

INSERT INTO card_list (neutral_mana, red_mana, name, card_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (3, 2, 'Magmaw', 'CREATURE', 'Elemental', 'magmaw.png', 'Ultimate Masters',
        'Sacrifice a nonland permanent: This creature deals 1 damage to any target.', 4, 4, 'UNCOMMON');

INSERT INTO card_list (neutral_mana, green_mana, name, card_type, sub_type, picture, set_name, rule_text, power, toughness, rarity)
VALUES (2, 1, 'Gus', 'CREATURE', 'Gus', 'Gus.png', 'Unglued',
        'This creature enters with a +1/+1 counter on it for each Magic game you have lost to one of your opponents since you last won a game against them.', 2, 2, 'COMMON');

INSERT INTO card_list (name, super_type, card_type, sub_type, picture, set_name, rarity)
VALUES ('Island', 'BASIC', 'LAND', 'Island', 'island.png', 'Secrets of Strixhaven', 'LAND');


--Commander kort
INSERT INTO card_list (neutral_mana, green_mana, name, super_type, card_type, sub_type, can_be_commander, picture, set_name, rule_text, power, toughness, rarity)
VALUES (2, 1, 'Shroofus Sproutsire', 'LEGENDARY', 'CREATURE', 'Saproling', true, 'Shroofus.png', 'Foundations Jumpstart',
        'Trample    Whenever a Saproling you control deals combat damage to a player, create that many 1/1 green Saproling creature tokens.', 1, 1, 'RARE');

INSERT INTO card_list (green_mana, red_mana, white_mana, name, super_type, card_type, sub_type, can_be_commander, picture, set_name, rule_text, power, toughness, rarity)
VALUES (1, 1, 1, 'Sigurd, Jarl of Ravensthorpe', 'LEGENDARY', 'CREATURE', 'Human Warrior', true, 'Sigurd.png', 'Assassins Creed',
        'Vigilance, trample, lifelink    Boast — 1 neutral mana: Put a lore counter on target Saga you control or remove one from it. (Activate only if this creature attacked this turn and only once each turn.)   Whenever you put a lore counter on a Saga you control, put a +1/+1 counter on up to one other target creature.', 3, 3, 'RARE');



--Brugere

--Password 1234frank
INSERT INTO user (name, email, password_hash, role)
VALUES ('Frank', 'Frank@gmail.com', '$2a$10$VxHaDOtbRR9P5aWhz1Nv.eVAB5gETUHv7K82SNYXnq4JOw7F8z72q', 'ADMIN');

--Password magicman1
INSERT INTO user (name, email, password_hash)
VALUES ('MagicMan', 'Magic@gmail.com', '$2a$10$kM4GSt4pPksh2NiPY5oxreDYngo28Ew4E/S7V6EPgPqarSht.OXam');


--deck
INSERT INTO deck (user_id, deck_name, format)
VALUES (2, 'Goofy', 'COMMANDER');

--deck contain card
INSERT INTO deck_contain_card (deck_id, card_id, is_commander)
VALUES (1, 12, true);

--deck
INSERT INTO deck (user_id, deck_name, format)
VALUES (2, 'WIP', 'COMMANDER');



--owned / collection
INSERT INTO owned_card (card_id, user_id)
VALUES (11, 2)



